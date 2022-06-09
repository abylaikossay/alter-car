package net.alterapp.altercar.service;

import net.alterapp.altercar.model.entity.CarBrandEntity;
import net.alterapp.altercar.model.entity.PartnerEntity;
import net.alterapp.altercar.model.requests.CarBrandRequest;
import net.alterapp.altercar.model.requests.PartnerRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PartnerService {


    PartnerEntity create(PartnerRequest partnerRequest);

    PartnerEntity getById(Long id);

    PartnerEntity update(PartnerRequest partnerRequest, Long id);

    void delete(Long id);

    List<PartnerEntity> getList();
}
