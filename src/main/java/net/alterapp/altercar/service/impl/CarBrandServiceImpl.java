package net.alterapp.altercar.service.impl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import net.alterapp.altercar.model.entity.CarBrandEntity;
import net.alterapp.altercar.model.requests.CarBrandRequest;
import net.alterapp.altercar.model.responses.errors.ErrorCode;
import net.alterapp.altercar.model.responses.errors.ServiceException;
import net.alterapp.altercar.repository.CarBrandRepository;
import net.alterapp.altercar.service.CarBrandService;
import net.alterapp.altercar.service.FileService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CarBrandServiceImpl implements CarBrandService {

    private final CarBrandRepository carBrandRepository;
    private final FileService fileService;


    @Override
    public CarBrandEntity create(CarBrandRequest carBrandRequest) {
        checkIfExists(carBrandRequest.getName());
        CarBrandEntity carBrandEntity = new CarBrandEntity();
        if (carBrandRequest.getLogo() != null) {
            String file = fileService.storeFile(carBrandRequest.getLogo(), "car-brand-logo");
            carBrandEntity.setLogoUrl(file);
        }
        carBrandEntity.setName(carBrandRequest.getName());
        carBrandRepository.save(carBrandEntity);
        return carBrandEntity;
    }

    private void checkIfExists(String name) {
        Optional<CarBrandEntity> carBrandEntity = carBrandRepository.findByName(name);
        if (carBrandEntity.isPresent()) {
            throw ServiceException.builder()
                    .errorCode(ErrorCode.ALREADY_EXISTS)
                    .httpStatus(HttpStatus.CONFLICT)
                    .message("Ошибка! Марка с таким наименованием уже существует")
                    .build();
        }
    }

    @Override
    public CarBrandEntity getById(Long id) {
        return carBrandRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No Car Brand Found! " + id));
    }

    @Override
    public CarBrandEntity update(CarBrandRequest carBrandRequest, Long id) {
        CarBrandEntity carBrandEntity = getById(id);
        if (carBrandRequest.getLogo() != null) {
            if (carBrandEntity.getLogoUrl() != null) {
                fileService.delete(carBrandEntity.getLogoUrl(), "car-brand-logo");
            }
            String logo_url = fileService.storeFile(carBrandRequest.getLogo(), "car-brand-logo");
            carBrandEntity.setLogoUrl(logo_url);
        }
        carBrandEntity.setName(carBrandRequest.getName());
        carBrandRepository.save(carBrandEntity);
        return carBrandEntity;
    }

    @Override
    public void delete(Long id) {
        CarBrandEntity carBrandEntity = getById(id);
        if (carBrandEntity.getLogoUrl() != null) {
            fileService.delete(carBrandEntity.getLogoUrl(), "car-brand-logo");
        }
        carBrandEntity.setDeletedAt(new Date());
        carBrandRepository.save(carBrandEntity);

    }

    @Override
    public List<CarBrandEntity> getList() {
        return carBrandRepository.findAllByDeletedAtIsNull();
    }

    @Override
    public void setBrandLogo(MultipartFile image, Long id) {
        CarBrandEntity carBrandEntity = getById(id);
        if (carBrandEntity.getLogoUrl() != null) {
            fileService.delete(carBrandEntity.getLogoUrl(), "car-brand-logo");
        }
        String logo_url = fileService.storeFile(image, "car-brand-logo");
        carBrandEntity.setLogoUrl(logo_url);
        carBrandRepository.save(carBrandEntity);

    }
}
