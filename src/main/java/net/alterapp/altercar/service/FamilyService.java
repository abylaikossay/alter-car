package net.alterapp.altercar.service;

import net.alterapp.altercar.model.account.AccountEditRequest;
import net.alterapp.altercar.model.entity.AccountEntity;
import net.alterapp.altercar.model.entity.FamilyEntity;
import net.alterapp.altercar.model.entity.UserInfoEntity;
import net.alterapp.altercar.model.entity.UserTemplateEntity;
import net.alterapp.altercar.model.userinfo.UserInfo;

import java.util.List;

public interface FamilyService {


    void sendInvite(String username, Long userId);

    FamilyEntity getFamilyById(Long id);

    FamilyEntity createFamily(String username);

}
