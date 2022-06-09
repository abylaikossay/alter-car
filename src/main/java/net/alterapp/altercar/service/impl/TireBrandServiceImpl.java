package net.alterapp.altercar.service.impl;

import lombok.RequiredArgsConstructor;
import net.alterapp.altercar.model.entity.TireBrandEntity;
import net.alterapp.altercar.model.requests.TireBrandRequest;
import net.alterapp.altercar.model.responses.errors.ErrorCode;
import net.alterapp.altercar.model.responses.errors.ServiceException;
import net.alterapp.altercar.repository.TireBrandRepository;
import net.alterapp.altercar.service.FileService;
import net.alterapp.altercar.service.TireBrandService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class TireBrandServiceImpl implements TireBrandService {

    private final TireBrandRepository tireBrandRepository;
    private final FileService fileService;


    @Override
    public TireBrandEntity create(TireBrandRequest tireBrandRequest) {
        checkIfExists(tireBrandRequest.getName());
        TireBrandEntity tireBrandEntity = new TireBrandEntity();
        if (tireBrandRequest.getLogo() != null) {
            String file = fileService.storeFile(tireBrandRequest.getLogo(), "tire-brand-logo");
            tireBrandEntity.setLogoUrl(file);
        }
        tireBrandEntity.setName(tireBrandRequest.getName());
        tireBrandRepository.save(tireBrandEntity);
        return tireBrandEntity;
    }

    private void checkIfExists(String name) {
        Optional<TireBrandEntity> tireBrandEntity = tireBrandRepository.findByName(name);
        if (tireBrandEntity.isPresent()) {
            throw ServiceException.builder()
                    .errorCode(ErrorCode.ALREADY_EXISTS)
                    .httpStatus(HttpStatus.CONFLICT)
                    .message("Ошибка! Марка с таким наименованием уже существует")
                    .build();
        }
    }

    @Override
    public TireBrandEntity getById(Long id) {
        return tireBrandRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No Tire Brand Found! " + id));
    }

    @Override
    public TireBrandEntity update(TireBrandRequest tireBrandRequest, Long id) {
        TireBrandEntity tireBrandEntity = getById(id);
        if (tireBrandRequest.getLogo() != null) {
            if (tireBrandEntity.getLogoUrl() != null) {
                fileService.delete(tireBrandEntity.getLogoUrl(), "tire-brand-logo");
            }
            String logo_url = fileService.storeFile(tireBrandRequest.getLogo(), "tire-brand-logo");
            tireBrandEntity.setLogoUrl(logo_url);
        }
        tireBrandEntity.setName(tireBrandRequest.getName());
        tireBrandRepository.save(tireBrandEntity);
        return tireBrandEntity;
    }

    @Override
    public void delete(Long id) {
        TireBrandEntity tireBrandEntity = getById(id);
        if (tireBrandEntity.getLogoUrl() != null) {
            fileService.delete(tireBrandEntity.getLogoUrl(), "tire-brand-logo");
        }
        tireBrandEntity.setDeletedAt(new Date());
        tireBrandRepository.save(tireBrandEntity);

    }

    @Override
    public List<TireBrandEntity> getList() {
        return tireBrandRepository.findAllByDeletedAtIsNull();
    }
}
