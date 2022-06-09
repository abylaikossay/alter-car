package net.alterapp.altercar.service.impl;

import lombok.RequiredArgsConstructor;
import net.alterapp.altercar.model.entity.*;
import net.alterapp.altercar.model.requests.UserCarRequest;
import net.alterapp.altercar.model.responses.errors.ErrorCode;
import net.alterapp.altercar.model.responses.errors.ServiceException;
import net.alterapp.altercar.repository.UserCarRepository;
import net.alterapp.altercar.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserCarServiceImpl implements UserCarService {

    private final UserCarRepository userCarRepository;
    private final AccountService accountService;
    private final UserInfoService userInfoService;
    private final CarBrandService carBrandService;
    private final CarModelService carModelService;
    private final UserCarHistoryService userCarHistoryService;
    private final FamilyService familyService;


    @Override
    @Transactional
    public UserCarEntity addCar(UserCarRequest userCarRequest, String username) {
        UserCarEntity userCarEntity = new UserCarEntity();
        AccountEntity accountEntity = accountService.getByUsername(username);
        userCarEntity.setAccount(accountEntity);
        userCarEntity.setCarBrand(carBrandService.getById(userCarRequest.getCarBrandId()));
        userCarEntity.setCarModel(carModelService.getById(userCarRequest.getCarModelId()));
        userCarEntity.setCarYear(userCarRequest.getCarYear());
        userCarEntity.setColor(userCarRequest.getColor());
        userCarEntity.setEngineVolume(userCarRequest.getEngineVolume());
        userCarEntity.setVinCode(userCarRequest.getVinCode());
        userCarRepository.save(userCarEntity);
        userCarHistoryService.addUserCarHistory(accountEntity, userCarEntity);
        return userCarEntity;
    }

    @Override
    public UserCarEntity getUserCarById(Long id, String username) {
        UserCarEntity userCarEntity = userCarRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No User car Found! " + id));
        System.out.println("USER CAR ENTITY: " + userCarEntity);
        System.out.println("USER CAR USername: " + username);
        return userCarEntity;
    }

    @Override
    public UserCarEntity updateUserCar(Long id, UserCarRequest userCarRequest, String username) {
        UserCarEntity userCarEntity = getUserCarById(id, username);
        AccountEntity accountEntity = accountService.getByUsername(username);
        if (!userCarEntity.getAccount().getId().equals(accountEntity.getId())) {
            throw ServiceException.builder().httpStatus(HttpStatus.FORBIDDEN)
                    .errorCode(ErrorCode.NOT_ALLOWED)
                    .message("Вы не имеете доступ к данной машине")
                    .build();
        }
        userCarEntity.setCarBrand(carBrandService.getById(userCarRequest.getCarBrandId()));
        userCarEntity.setCarModel(carModelService.getById(userCarRequest.getCarModelId()));
        userCarEntity.setCarYear(userCarRequest.getCarYear());
        userCarEntity.setColor(userCarRequest.getColor());
        userCarEntity.setEngineVolume(userCarRequest.getEngineVolume());
        userCarEntity.setVinCode(userCarRequest.getVinCode());
        userCarRepository.save(userCarEntity);
        return userCarEntity;
    }

    @Override
    public List<UserCarEntity> getAllUserCars(String username) {
        List<UserCarEntity> userCars = new ArrayList<>();
        AccountEntity accountEntity = accountService.getByUsername(username);
        UserInfoEntity userInfoEntity = userInfoService.getByUserId(accountEntity.getId());
        boolean isParent = false;
        Long familyId = null;
        if (userInfoEntity.getFamily() != null) {
            FamilyEntity familyEntity = familyService.getFamilyById(userInfoEntity.getFamily().getId());
            if (accountEntity.getId().equals(familyEntity.getFamilyParent().getId())) {
                isParent = true;
                familyId = familyEntity.getId();
            }
        }

        if (isParent) {
            List<UserInfoEntity> userInfoList = userInfoService.findAllFamilyMembers(familyId);
            List<Long> accountIds = new ArrayList<>();
            userInfoList.forEach(userInfoElement -> {
                accountIds.add(userInfoElement.getAccount().getId());
            });
            userCars = userCarRepository.findAllByAccount_IdIn(accountIds);
        } else {
            userCars = userCarRepository.findAllByAccount_Id(accountEntity.getId());
        }

        return userCars;
    }

    @Override
    @Transactional
    public void giftCar(String username, String userPhone, Long carId) {
        AccountEntity accountSender = accountService.getByUsername(username);
        AccountEntity accountReceiver = accountService.getByUsername(username);
        UserCarEntity userCar = userCarRepository.findById(carId).orElseThrow(() -> new EntityNotFoundException("No User car Found! " + carId));
        if (!userCar.getAccount().getId().equals(accountSender.getId())) {
            throw ServiceException.builder().httpStatus(HttpStatus.FORBIDDEN)
                    .errorCode(ErrorCode.NOT_ALLOWED)
                    .message("Вы не имеете доступ к данной машине")
                    .build();
        }
        userCar.setAccount(accountReceiver);
        userCarRepository.save(userCar);
        userCarHistoryService.giftUserCar(accountSender, accountReceiver, userCar);
    }
}
