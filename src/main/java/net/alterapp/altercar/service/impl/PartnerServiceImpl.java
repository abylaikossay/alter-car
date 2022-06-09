package net.alterapp.altercar.service.impl;

import lombok.RequiredArgsConstructor;
import net.alterapp.altercar.model.entity.PartnerEntity;
import net.alterapp.altercar.model.requests.PartnerRequest;
import net.alterapp.altercar.model.responses.errors.ErrorCode;
import net.alterapp.altercar.model.responses.errors.ServiceException;
import net.alterapp.altercar.repository.PartnerRepository;
import net.alterapp.altercar.service.FileService;
import net.alterapp.altercar.service.PartnerService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class PartnerServiceImpl implements PartnerService {

    private final PartnerRepository partnerRepository;
    private final FileService fileService;


    @Override
    public PartnerEntity create(PartnerRequest partnerRequest, String userName) {
        checkIfExists(partnerRequest.getName());
        PartnerEntity partnerEntity = new PartnerEntity();
        if (partnerRequest.getLogo() != null) {
            String file = fileService.storeFile(partnerRequest.getLogo(), "partner-logo");
            partnerEntity.setLogoUrl(file);
        }
        partnerEntity.setName(partnerRequest.getName());
        partnerEntity.setAddress(partnerRequest.getAddress());
        partnerEntity.setDescription(partnerRequest.getDescription());
        partnerEntity.setEmail(partnerRequest.getEmail());
        partnerEntity.setLink(partnerRequest.getLink());
        partnerEntity.setPhone(partnerRequest.getPhone());
        partnerEntity.setPushText(partnerRequest.getPushText());
        partnerEntity.setVisible(partnerRequest.getVisible());
        partnerEntity.setQrPath(partnerRequest.getQrPath());
        partnerRepository.save(partnerEntity);
        return partnerEntity;
    }

    private void checkIfExists(String name) {
        Optional<PartnerEntity> partnerEntity = partnerRepository.findByName(name);
        if (partnerEntity.isPresent()) {
            throw ServiceException.builder()
                    .errorCode(ErrorCode.ALREADY_EXISTS)
                    .httpStatus(HttpStatus.CONFLICT)
                    .message("Ошибка! Магазин с таким наименованием уже существует")
                    .build();
        }
    }

    @Override
    public PartnerEntity getById(Long id) {
        return partnerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No Partner Brand Found! " + id));
    }

    @Override
    public PartnerEntity update(PartnerRequest partnerRequest, Long id) {
        PartnerEntity partnerEntity = getById(id);
        if (partnerRequest.getLogo() != null) {
            if (partnerEntity.getLogoUrl() != null) {
                fileService.delete(partnerEntity.getLogoUrl(), "partner-logo");
            }
            String logo_url = fileService.storeFile(partnerRequest.getLogo(), "partner-logo");
            partnerEntity.setLogoUrl(logo_url);
        }
        partnerEntity.setName(partnerRequest.getName());
        partnerEntity.setAddress(partnerRequest.getAddress());
        partnerEntity.setDescription(partnerRequest.getDescription());
        partnerEntity.setEmail(partnerRequest.getEmail());
        partnerEntity.setLink(partnerRequest.getLink());
        partnerEntity.setPhone(partnerRequest.getPhone());
        partnerEntity.setPushText(partnerRequest.getPushText());
        partnerEntity.setVisible(partnerRequest.getVisible());
        partnerEntity.setQrPath(partnerRequest.getQrPath());
        partnerRepository.save(partnerEntity);
        return partnerEntity;
    }

    @Override
    public void delete(Long id) {
        PartnerEntity partnerEntity = getById(id);
        if (partnerEntity.getLogoUrl() != null) {
            fileService.delete(partnerEntity.getLogoUrl(), "partner-logo");
        }
        partnerEntity.setDeletedAt(new Date());
        partnerRepository.save(partnerEntity);

    }

    @Override
    public List<PartnerEntity> getList() {
        return partnerRepository.findAllByDeletedAtIsNull();
    }

}
