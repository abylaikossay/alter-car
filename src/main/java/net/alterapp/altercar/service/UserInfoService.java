package net.alterapp.altercar.service;

import net.alterapp.altercar.model.account.AccountEditRequest;
import net.alterapp.altercar.model.entity.AccountEntity;
import net.alterapp.altercar.model.entity.UserInfoEntity;
import net.alterapp.altercar.model.entity.UserTemplateEntity;
import net.alterapp.altercar.model.userinfo.UserInfo;
import net.alterapp.altercar.model.userinfo.UserInfoRecord;

import java.util.List;

public interface UserInfoService {

    UserInfo getById(Long id);

    UserInfoEntity getByUserId(Long userId);

    List<UserInfoEntity> findAllFamilyMembers(Long familyId);


    UserInfoEntity create(AccountEntity account, AccountEditRequest request);

    UserInfoEntity createRegisteredUser(AccountEntity account, UserTemplateEntity userTemplateEntity);

    void save(UserInfoEntity userInfoEntity);
}
