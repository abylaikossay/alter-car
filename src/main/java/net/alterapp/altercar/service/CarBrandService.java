package net.alterapp.altercar.service;

import net.alterapp.altercar.model.entity.CarBrandEntity;
import net.alterapp.altercar.model.requests.CarBrandRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CarBrandService {


    CarBrandEntity create(CarBrandRequest carBrandRequest);

    CarBrandEntity getById(Long id);

    CarBrandEntity update(CarBrandRequest carBrandRequest, Long id);

    void delete(Long id);

    List<CarBrandEntity> getList();

    void setBrandLogo(MultipartFile image, Long id);
}
