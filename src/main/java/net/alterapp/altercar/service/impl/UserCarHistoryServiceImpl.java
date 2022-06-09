package net.alterapp.altercar.service.impl;

import lombok.RequiredArgsConstructor;
import net.alterapp.altercar.model.entity.AccountEntity;
import net.alterapp.altercar.model.entity.UserCarEntity;
import net.alterapp.altercar.model.entity.UserCarHistoryEntity;
import net.alterapp.altercar.repository.UserCarHistoryRepository;
import net.alterapp.altercar.service.*;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class UserCarHistoryServiceImpl implements UserCarHistoryService {

    private final UserCarHistoryRepository userCarHistoryRepository;


    @Override
    public void addUserCarHistory(AccountEntity accountEntity, UserCarEntity userCarEntity) {
        UserCarHistoryEntity userCarHistoryEntity = new UserCarHistoryEntity();
        userCarHistoryEntity.setCurrentAccount(accountEntity);
        userCarHistoryEntity.setPreviousAccount(null);
        userCarHistoryEntity.setUserCar(userCarEntity);
        userCarHistoryRepository.save(userCarHistoryEntity);
    }

    @Override
    public void giftUserCar(AccountEntity previousAccount, AccountEntity currentAccount, UserCarEntity userCarEntity) {
        UserCarHistoryEntity userCarHistoryEntity = new UserCarHistoryEntity();
        userCarHistoryEntity.setPreviousAccount(previousAccount);
        userCarHistoryEntity.setCurrentAccount(currentAccount);
        userCarHistoryEntity.setUserCar(userCarEntity);
        userCarHistoryRepository.save(userCarHistoryEntity);
    }
}
