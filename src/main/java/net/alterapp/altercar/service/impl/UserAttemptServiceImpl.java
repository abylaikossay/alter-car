package net.alterapp.altercar.service.impl;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import net.alterapp.altercar.model.account.Account;
import net.alterapp.altercar.model.entity.AccountEntity;
import net.alterapp.altercar.model.entity.UserAttemptEntity;
import net.alterapp.altercar.model.entity.UserTemplateEntity;
import net.alterapp.altercar.repository.UserAttemptsRepository;
import net.alterapp.altercar.service.UserAttemptService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserAttemptServiceImpl implements UserAttemptService {

    private final UserAttemptsRepository userAttemptsRepository;


    @Override
    public UserAttemptEntity getUserAttemptByAccountEntity(AccountEntity user) {
        UserAttemptEntity userAttempt = userAttemptsRepository.findDistinctByAccountEntity_Id(user.getId());
        if (userAttempt != null) {
            return userAttempt;
        } else {
            UserAttemptEntity userAttemptNew = new UserAttemptEntity();
            userAttemptNew.setAccountEntity(user);
            userAttemptNew.setRegistrationAttempts(0);
            userAttemptsRepository.save(userAttemptNew);
            return userAttemptNew;
        }
    }

    @Override
    public UserAttemptEntity getUserAttemptByUserTemplate(UserTemplateEntity user) {
        UserAttemptEntity userAttempt = userAttemptsRepository.findDistinctByUserTemplate_Id(user.getId());
        if (userAttempt != null) {
            return userAttempt;
        } else {
            UserAttemptEntity userAttemptNew = new UserAttemptEntity();
            userAttemptNew.setUserTemplate(user);
            userAttemptNew.setRegistrationAttempts(0);
            userAttemptsRepository.save(userAttemptNew);
            return userAttemptNew;
        }
    }

    @Override
    public void save(UserAttemptEntity userAttempt) {
        userAttemptsRepository.save(userAttempt);
    }

    @Override
    public void checkAllUsersTime() {
        List<UserAttemptEntity> userAttemptList = userAttemptsRepository.getAllUsers();
        userAttemptList.forEach(userAttempt -> {
            long diff = new Date().getTime() - userAttempt.getUpdatedAt().getTime();
            long diffMinutes = diff / (60 * 1000);
            if (diffMinutes > 60) {
                userAttempt.setRegistrationAttempts(0);
                userAttemptsRepository.save(userAttempt);
            }
        });

    }
}
