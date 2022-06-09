package net.alterapp.altercar.service.impl;

import io.jsonwebtoken.lang.Assert;
import lombok.ToString;
import net.alterapp.altercar.model.account.AccountEditRequest;
import net.alterapp.altercar.model.entity.AccountEntity;
import net.alterapp.altercar.model.entity.PartnerEntity;
import net.alterapp.altercar.model.entity.UserInfoEntity;
import net.alterapp.altercar.model.entity.UserTemplateEntity;
import net.alterapp.altercar.model.enums.GenderEnum;
import net.alterapp.altercar.model.userinfo.UserInfo;
import net.alterapp.altercar.model.userinfo.UserInfoRecord;
import net.alterapp.altercar.repository.UserInfoRepository;
import net.alterapp.altercar.service.PartnerService;
import net.alterapp.altercar.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoRepository repository;
    private final PartnerService partnerService;

    @Override
    @Transactional
    public UserInfo getById(Long id) {
        Assert.notNull(id, "Argument 'id' cannot be null");

        UserInfoEntity entity = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("No UserInfo Found! " + id));


        return UserInfo.builder()
                .id(entity.getId())
                .username(entity.getAccount().getUsername())
                .surname(entity.getSurname())
                .name(entity.getName())
                .patronymic(entity.getPatronymic())
                .build();
    }

    @Override
    @Transactional
    public UserInfoEntity getByUserId(Long userId) {
        Assert.notNull(userId, "Argument 'id' cannot be null");

      return repository.findByAccount_Id(userId).orElseThrow(() -> new EntityNotFoundException("No UserInfo Found! " + userId));
    }

    @Override
    public List<UserInfoEntity> findAllFamilyMembers(Long familyId) {
        return repository.findAllByFamily_Id(familyId);
    }


    @Override
    @Transactional
    public UserInfoEntity create(AccountEntity account, AccountEditRequest request) {
        Assert.notNull(request, "Argument 'account' cannot be null");
        Assert.notNull(request, "Argument 'request' cannot be null");

        UserInfoEntity userInfo = new UserInfoEntity();
        userInfo.setAccount(account);
        userInfo.setSurname(request.surname);
        userInfo.setName(request.name);
        userInfo.setPatronymic(request.patronymic);
        if (request.getPartnerId() != null) {
            PartnerEntity partnerEntity = partnerService.getById(request.getPartnerId());
            userInfo.setPartner(partnerEntity);
        }

        return repository.save(userInfo);
    }

    @Override
    public UserInfoEntity createRegisteredUser(AccountEntity account, UserTemplateEntity userTemplateEntity) {
        UserInfoEntity userInfo = new UserInfoEntity();
        userInfo.setAccount(account);
        userInfo.setSurname(userTemplateEntity.getSurname());
        userInfo.setName(userTemplateEntity.getName());
        userInfo.setPatronymic(userTemplateEntity.getPatronymic());
        userInfo.setCityId(userTemplateEntity.getCityId());
        userInfo.setBirthDate(userTemplateEntity.getBirthDate());
        userInfo.setEmail(userTemplateEntity.getEmail());
        userInfo.setGender(userTemplateEntity.getGender());
        userInfo.setLanguage(userTemplateEntity.getLanguage());
        userInfo.setPlatform(userTemplateEntity.getPlatform());
        userInfo.setDeviceToken(userTemplateEntity.getDeviceToken());
        userInfo.setDeviceToken(userTemplateEntity.getDeviceToken());
        return repository.save(userInfo);
    }

  @Override
  public void save(UserInfoEntity userInfoEntity) {
    repository.save(userInfoEntity);
  }
}
