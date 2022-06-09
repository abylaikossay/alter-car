package net.alterapp.altercar.service.impl;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import net.alterapp.altercar.model.entity.CarBrandEntity;
import net.alterapp.altercar.model.entity.CarModelEntity;
import net.alterapp.altercar.model.requests.CarModelRequest;
import net.alterapp.altercar.model.responses.errors.ErrorCode;
import net.alterapp.altercar.model.responses.errors.ServiceException;
import net.alterapp.altercar.repository.CarModelRepository;
import net.alterapp.altercar.service.CarBrandService;
import net.alterapp.altercar.service.CarModelService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarModelServiceImpl implements CarModelService {
    
    private final CarModelRepository carModelRepository;
    private final CarBrandService carBrandService;



    @Override
    public void create(CarModelRequest carModelRequest) {
        checkIfExists(carModelRequest.getName());
        CarBrandEntity carBrandEntity = carBrandService.getById(carModelRequest.getCarBrandId());
        CarModelEntity carModelEntity = CarModelEntity.builder()
                .name(carModelRequest.getName())
                .carBrand(carBrandEntity)
                .build();
        carModelRepository.save(carModelEntity);
    }

    private void checkIfExists(String name) {
        Optional<CarModelEntity> carModelEntity = carModelRepository.findByName(name);
        if (carModelEntity.isPresent()) {
            throw ServiceException.builder()
                    .errorCode(ErrorCode.ALREADY_EXISTS)
                    .httpStatus(HttpStatus.CONFLICT)
                    .message("Ошибка! Модель с таким наименованием уже существует")
                    .build();
        }
    }

    @Override
    public CarModelEntity getById(Long id) {
        return carModelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No Car Model Found! " + id));
    }

    @Override
    public void update(CarModelRequest carModelRequest, Long id) {
        CarModelEntity carModelEntity = getById(id);
        CarBrandEntity carBrandEntity = carBrandService.getById(carModelRequest.getCarBrandId());
        carModelEntity.setName(carModelRequest.getName());
        carModelEntity.setCarBrand(carBrandEntity);
        carModelRepository.save(carModelEntity);
    }

    @Override
    public void delete(Long id) {
        CarModelEntity carModelEntity = getById(id);
        carModelEntity.setDeletedAt(new Date());
        carModelRepository.save(carModelEntity);
    }

    @Override
    public List<CarModelEntity> getList() {
        return carModelRepository.findAllByDeletedAtIsNull();
    }
}
