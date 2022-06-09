package net.alterapp.altercar.service;


import net.alterapp.altercar.model.entity.AccountEntity;
import net.alterapp.altercar.model.entity.UserAttemptEntity;
import net.alterapp.altercar.model.entity.UserTemplateEntity;

public interface UserAttemptService {

    UserAttemptEntity getUserAttemptByAccountEntity(AccountEntity user);
    UserAttemptEntity getUserAttemptByUserTemplate(UserTemplateEntity user);
    void save(UserAttemptEntity userAttempt);
    void checkAllUsersTime();

}
