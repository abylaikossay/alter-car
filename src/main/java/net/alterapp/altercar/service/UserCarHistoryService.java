package net.alterapp.altercar.service;


import net.alterapp.altercar.model.entity.AccountEntity;
import net.alterapp.altercar.model.entity.UserCarEntity;

public interface UserCarHistoryService {

    void addUserCarHistory(AccountEntity accountEntity, UserCarEntity userCarEntity);

    void giftUserCar(AccountEntity previousAccount, AccountEntity currentAccount, UserCarEntity userCarEntity);

}
