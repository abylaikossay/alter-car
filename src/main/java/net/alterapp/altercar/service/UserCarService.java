package net.alterapp.altercar.service;

import net.alterapp.altercar.model.entity.UserCarEntity;
import net.alterapp.altercar.model.requests.UserCarRequest;

import java.util.List;

public interface UserCarService {


    UserCarEntity addCar(UserCarRequest userCarRequest, String username);

    UserCarEntity getUserCarById(Long id, String username);

    UserCarEntity updateUserCar(Long id, UserCarRequest userCarRequest, String username);

    List<UserCarEntity> getAllUserCars(String username);

    void giftCar(String username, String userPhone, Long carId);
}
