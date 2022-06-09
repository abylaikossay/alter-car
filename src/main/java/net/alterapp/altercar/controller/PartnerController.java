package net.alterapp.altercar.controller;

import lombok.AllArgsConstructor;
import net.alterapp.altercar.model.requests.CarBrandRequest;
import net.alterapp.altercar.model.requests.PartnerRequest;
import net.alterapp.altercar.model.responses.errors.ServiceException;
import net.alterapp.altercar.model.responses.success.SuccessResponse;
import net.alterapp.altercar.service.CarBrandService;
import net.alterapp.altercar.service.PartnerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/partner")
public class PartnerController extends BaseController {

    private PartnerService partnerService;


    @PostMapping(value = "/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> create(@ModelAttribute PartnerRequest partnerRequest) throws ServiceException {
        return buildResponse(partnerService.create(partnerRequest), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) throws ServiceException {
        return buildResponse(partnerService.getById(id), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<?> getBrandList() throws ServiceException {
        return buildResponse(partnerService.getList(), HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> update(@ModelAttribute PartnerRequest partnerRequest,
                                    @PathVariable Long id) throws ServiceException {
        return buildResponse(partnerService.update(partnerRequest, id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws ServiceException {
        partnerService.delete(id);
        return buildSuccessResponse(SuccessResponse.builder().message("Партнер успешно удален").build());
    }
}
