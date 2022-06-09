package net.alterapp.altercar.service;

import net.alterapp.altercar.model.account.AccountEditRequest;
import net.alterapp.altercar.model.account.AccountRegisterRequest;
import net.alterapp.altercar.model.account.AccountUpdateRoleRequest;
import net.alterapp.altercar.model.entity.AccountEntity;
import net.alterapp.altercar.model.entity.UserTemplateEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AccountService {

    AccountEntity getById(Long id);

    AccountEntity getByUsername(String username);

    AccountEntity create(AccountEditRequest request);

    AccountEntity createUserFromTemplate(UserTemplateEntity userTemplateEntity);

    AccountEntity save(AccountEntity account);

    void updateRoles(Long accountId, AccountUpdateRoleRequest request);

    // Registration
    void registerUser(AccountRegisterRequest registerRequest);

    void confirmRegister(String phone, String registrationCode);

    void resendSmsToConfirm(String phone);


    // Profile
    void changeUserAvatar(MultipartFile image, String userName);



}
