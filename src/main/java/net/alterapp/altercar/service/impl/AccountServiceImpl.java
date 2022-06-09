package net.alterapp.altercar.service.impl;

import io.jsonwebtoken.lang.Assert;
import net.alterapp.altercar.config.api.ApiException;
import net.alterapp.altercar.config.api.ApiValidationError;
import net.alterapp.altercar.model.account.AccountRegisterRequest;
import net.alterapp.altercar.model.entity.*;
import net.alterapp.altercar.model.enums.RoleEnum;
import net.alterapp.altercar.model.account.AccountEditRequest;
import net.alterapp.altercar.model.account.AccountUpdateRoleRequest;
import net.alterapp.altercar.model.responses.errors.ErrorCode;
import net.alterapp.altercar.model.responses.errors.ServiceException;
import net.alterapp.altercar.model.userinfo.UserInfo;
import net.alterapp.altercar.repository.AccountRepository;
import net.alterapp.altercar.repository.RoleRepository;
import net.alterapp.altercar.repository.UserTemplateRepository;
import net.alterapp.altercar.service.AccountService;
import lombok.RequiredArgsConstructor;
import net.alterapp.altercar.service.FileService;
import net.alterapp.altercar.service.UserAttemptService;
import net.alterapp.altercar.service.UserInfoService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserInfoService userInfoService;
    private final UserTemplateRepository userTemplateRepository;
    private final UserAttemptService userAttemptService;
    private final FileService fileService;

    @Override
    public AccountEntity getById(Long id) {
        Assert.notNull(id, "Argument id cannot be null");
        return accountRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No Account Found! " + id));
    }


    @Override
    public AccountEntity getByUsername(String username) {
        Assert.notNull(username, "Argument username cannot be null");
        return accountRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public AccountEntity create(AccountEditRequest request) {
        Assert.notNull(request, "Argument request cannot be null");

        AccountEntity account = new AccountEntity();
        account.setUsername(request.username);
        account.setPassword(passwordEncoder.encode(request.password));

        if (account.getRole() == null) {
            account.setRole(RoleEntity.builder()
                    .id(RoleEnum.ROLE_BASIC)
                    .build());
        } else {
            account.setRole(RoleEntity.builder()
                    .id(request.role)
                    .build());
        }

        try {
            account.setUserInfo(userInfoService.create(account, request));

            return accountRepository.save(account);
        } catch (DataIntegrityViolationException ex) {
            if (ex.getCause() instanceof ConstraintViolationException
                    && "account_username_key".equals(((ConstraintViolationException) ex.getCause()).getConstraintName())) {
                throw new ApiException(
                        Collections.singletonList(
                                ApiValidationError.builder()
                                        .message("Username already exists!") // TODO: 26.11.2021 Localize
                                        .field("username")
                                        .build()
                        )
                );
            }
            throw ex;
        }
    }

    @Override
    public AccountEntity createUserFromTemplate(UserTemplateEntity userTemplateEntity) {
        Assert.notNull(userTemplateEntity, "Argument request cannot be null");

        AccountEntity account = new AccountEntity();
        account.setUsername(userTemplateEntity.getUsername());
        account.setPassword(userTemplateEntity.getPassword());


        account.setRole(RoleEntity.builder()
                .id(RoleEnum.ROLE_USER)
                .build());

        try {
            account.setUserInfo(userInfoService.createRegisteredUser(account, userTemplateEntity));

            return accountRepository.save(account);
        } catch (DataIntegrityViolationException ex) {
            if (ex.getCause() instanceof ConstraintViolationException
                    && "account_username_key".equals(((ConstraintViolationException) ex.getCause()).getConstraintName())) {
                throw new ApiException(
                        Collections.singletonList(
                                ApiValidationError.builder()
                                        .message("Username already exists!") // TODO: 26.11.2021 Localize
                                        .field("username")
                                        .build()
                        )
                );
            }
            throw ex;
        }
    }

    @Override
    public AccountEntity save(AccountEntity account) {
        Assert.notNull(account, "Argument 'account' cannot be null");
        return accountRepository.save(account);
    }

    @Override
    @Transactional
    public void updateRoles(Long accountId, AccountUpdateRoleRequest request) {
        Assert.notNull(accountId, "Argument 'accountId' cannot be null");
        Assert.notNull(accountId, "Argument 'request' cannot be null");

        if (request.getRole().equals(RoleEnum.ROLE_SYSTEM)) {
            throw new RuntimeException("Role ROLE_SYSTEM cannot be given!");
        }

        AccountEntity account = getById(accountId);

        Optional<RoleEntity> roleEntity = roleRepository.findById(request.getRole());
        if (roleEntity.isEmpty()) {
            throw new RuntimeException("Role " + request.getRole() + " cannot be given!");
        }
        account.setRole(roleEntity.get());

        accountRepository.save(account);
    }

    @Override
    public void registerUser(AccountRegisterRequest registerRequest) {
        checkIfUserExists(registerRequest.getPhone());
        String password = "password";
        if (registerRequest.getPassword() != null && !registerRequest.getPassword().isEmpty()) {
            password = registerRequest.getPassword();
        }
        UserTemplateEntity userTemplate = new UserTemplateEntity();
        userTemplate.setPassword(passwordEncoder.encode(password));
        userTemplate.setName(registerRequest.getName());
        userTemplate.setSurname(registerRequest.getSurname());

        userTemplate.setUsername(registerRequest.getPhone());

        userTemplate.setCityId(registerRequest.getCityId());
        userTemplate.setBirthDate(registerRequest.getBirthDate());
        userTemplate.setEmail(registerRequest.getEmail());
        userTemplate.setPlatform(registerRequest.getPlatform());
        userTemplate.setGender(registerRequest.getGender());
        userTemplate.setLanguage(registerRequest.getLanguage());
        String smsConfirmCode = generateRandomNumber();
        userTemplate.setActivationCode(smsConfirmCode);
        userTemplate.setRole(RoleEntity.builder()
                .id(RoleEnum.ROLE_USER)
                .build());
        Optional<UserTemplateEntity> checkedUserTemplate = userTemplateRepository.findByUsername(registerRequest.getPhone());
        checkedUserTemplate.ifPresent(userTemplateEntity -> userTemplate.setId(userTemplateEntity.getId()));
        userTemplateRepository.save(userTemplate);
    }

    @Override
    public void confirmRegister(String phone, String registrationCode) {
        checkIfUserExists(phone);
        Optional<UserTemplateEntity> userTemplate = userTemplateRepository.findByUsername(phone);
        if (userTemplate.isEmpty()) {
            throw ServiceException.builder()
                    .errorCode(ErrorCode.INVALID_EMPLOYEE)
                    .httpStatus(HttpStatus.CONFLICT)
                    .message("Ошибка! Пользователь не найден")
                    .build();
        }
        UserAttemptEntity userAttempt = userAttemptService.getUserAttemptByUserTemplate(userTemplate.get());
        if (userAttempt.getRegistrationAttempts() > 5) {
            throw ServiceException.builder().httpStatus(HttpStatus.FORBIDDEN)
                    .errorCode(ErrorCode.TOO_MANY_ATTEMPTS)
                    .message("Слишком много попыток, попоробуйте через 30 минут")
                    .build();
        }
        userAttempt.setRegistrationAttempts(userAttempt.getRegistrationAttempts() + 1);
        userAttemptService.save(userAttempt);
        if (!userTemplate.get().getActivationCode().equals(registrationCode)) {
            throw ServiceException.builder().httpStatus(HttpStatus.BAD_REQUEST)
                    .errorCode(ErrorCode.INVALID_ARGUMENT)
                    .message("Не правильный код!")
                    .build();
        }
        createUserFromTemplate(userTemplate.get());
    }

    @Override
    public void resendSmsToConfirm(String phone) {
        Optional<UserTemplateEntity> userTemplate = userTemplateRepository.findByUsername(phone);
        if (userTemplate.isEmpty()) {
            throw ServiceException.builder()
                    .errorCode(ErrorCode.INVALID_EMPLOYEE)
                    .httpStatus(HttpStatus.CONFLICT)
                    .message("Ошибка! Пользователь не найден")
                    .build();
        }
        String smsConfirmCode = generateRandomNumber();
        userTemplate.get().setActivationCode(smsConfirmCode);
        userTemplateRepository.save(userTemplate.get());
//        UserToSmsResponse userToSmsResponse = new UserToSmsResponse();
//        userToSmsResponse.setUserId(userTemplate.getId());
//        userToSmsResponse.setUserPhone(userTemplate.getPhone());
//        userToSmsResponse.setSmsConfirmCode(smsConfirmCode);
//        smsSender.SendSmsToUser(userToSmsResponse, "resendSms");
    }

    @Override
    public void changeUserAvatar(MultipartFile image, String userName) {
        AccountEntity user = accountRepository.findByUsername(userName);

        UserInfoEntity userInfoEntity = userInfoService.getByUserId(user.getId());
        String avatar_url = fileService.storeFile(image, "avatar");
        userInfoEntity.setAvatarUrl(avatar_url);
        userInfoService.save(userInfoEntity);
    }

    public void checkIfUserExists(String username) {
        AccountEntity userOptional = accountRepository.findByUsername(username);
        if (userOptional != null) {
            throw ServiceException.builder()
                    .errorCode(ErrorCode.USER_ALREADY_EXISTS)
                    .httpStatus(HttpStatus.CONFLICT)
                    .message("Пользователь уже зарегистрирован")
                    .build();
        }
    }

    public String generateRandomNumber() {
        return Integer.toString(ThreadLocalRandom.current().nextInt(100000, 1000000));
    }

}
