package net.alterapp.altercar.service.impl;

import lombok.RequiredArgsConstructor;
import net.alterapp.altercar.model.entity.AccountEntity;
import net.alterapp.altercar.model.entity.FamilyEntity;
import net.alterapp.altercar.model.entity.UserInfoEntity;
import net.alterapp.altercar.model.responses.errors.ErrorCode;
import net.alterapp.altercar.model.responses.errors.ServiceException;
import net.alterapp.altercar.repository.FamilyRepository;
import net.alterapp.altercar.service.AccountService;
import net.alterapp.altercar.service.FamilyService;
import net.alterapp.altercar.service.UserInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FamilyServiceImpl implements FamilyService {

    private final AccountService accountService;
    private final UserInfoService userInfoService;
    private final FamilyRepository familyRepository;


    @Override
    public void sendInvite(String username, Long userId) {
        AccountEntity accountEntity = accountService.getByUsername(username);
        Optional<FamilyEntity> familyEntityOptional = familyRepository.findByFamilyParent_Id(accountEntity.getId());
        if (familyEntityOptional.isEmpty()) {
            throw ServiceException.builder()
                    .errorCode(ErrorCode.ALREADY_EXISTS)
                    .httpStatus(HttpStatus.CONFLICT)
                    .message("Ошибка! Вы не можете отправлять приглашение!")
                    .build();
        }
        UserInfoEntity userInfoEntity = userInfoService.getByUserId(userId);
        userInfoEntity.setFamily(familyEntityOptional.get());
        userInfoService.save(userInfoEntity);


//        AccountEntity accountEntity = accountService.getByUsername(username);
//        FamilyEntity familyEntity;
//        Optional<FamilyEntity> familyEntityOptional = familyRepository.findDistinctByFamilyParent_Id(accountEntity.getId());
//        if (familyEntityOptional.isPresent()) {
//            familyEntity = familyEntityOptional.get();
//        } else {
//            familyEntity = new FamilyEntity();
//            familyEntity.setFamilyParent(accountEntity);
//            familyRepository.save(familyEntity);
//        }
//        UserInfoEntity userInfoEntity = userInfoService.getByUserId(userId);
//        userInfoEntity.setFamily(familyEntity);
    }

    @Override
    public FamilyEntity getFamilyById(Long id) {
        return familyRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No Family Found! " + id));
    }

    @Override
    public FamilyEntity createFamily(String username) {
        AccountEntity accountEntity = accountService.getByUsername(username);
        UserInfoEntity userInfoEntity = userInfoService.getByUserId(accountEntity.getId());
        if (userInfoEntity.getFamily() != null) {
            throw ServiceException.builder()
                    .errorCode(ErrorCode.ALREADY_EXISTS)
                    .httpStatus(HttpStatus.CONFLICT)
                    .message("Ошибка! У вас уже есть семья")
                    .build();
        }
//        Optional<FamilyEntity> familyEntityOptional = familyRepository.findDistinctByFamilyParent_Id(accountEntity.getId());
//        if (familyEntityOptional.isPresent()) {
//            throw ServiceException.builder()
//                    .errorCode(ErrorCode.ALREADY_EXISTS)
//                    .httpStatus(HttpStatus.CONFLICT)
//                    .message("Ошибка! У вас уже есть семья")
//                    .build();
//        }
        FamilyEntity familyEntity = new FamilyEntity();
        familyEntity.setFamilyParent(accountEntity);
        return familyRepository.save(familyEntity);
    }


}
