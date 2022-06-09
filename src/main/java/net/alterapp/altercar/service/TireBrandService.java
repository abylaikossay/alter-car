package net.alterapp.altercar.service;

import net.alterapp.altercar.model.entity.TireBrandEntity;
import net.alterapp.altercar.model.requests.TireBrandRequest;

import java.util.List;

public interface TireBrandService {


    TireBrandEntity create(TireBrandRequest tireBrandRequest);

    TireBrandEntity update(TireBrandRequest tireBrandRequest, Long id);

    TireBrandEntity getById(Long id);

    void delete(Long id);

    List<TireBrandEntity> getList();


}
