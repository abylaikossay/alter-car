package net.alterapp.altercar.service;

import net.alterapp.altercar.model.entity.CarModelEntity;
import net.alterapp.altercar.model.requests.CarModelRequest;

import java.util.List;

public interface CarModelService {


    void create(CarModelRequest carModelRequest);

    CarModelEntity getById(Long id);

    void update(CarModelRequest carModelRequest, Long id);

    void delete(Long id);

    List<CarModelEntity> getList();

}
