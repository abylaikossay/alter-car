package net.alterapp.altercar.controller;

import lombok.AllArgsConstructor;
import net.alterapp.altercar.model.requests.TireBrandRequest;
import net.alterapp.altercar.model.responses.errors.ServiceException;
import net.alterapp.altercar.model.responses.success.SuccessResponse;
import net.alterapp.altercar.service.TireBrandService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/tire-brand")
public class TireBrandController extends BaseController {

    private TireBrandService tireBrandService;


    @PostMapping(value = "/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> create(@ModelAttribute TireBrandRequest tireBrandRequest) throws ServiceException {
        return buildResponse(tireBrandService.create(tireBrandRequest), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) throws ServiceException {
        return buildResponse(tireBrandService.getById(id), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<?> getBrandList() throws ServiceException {
        return buildResponse(tireBrandService.getList(), HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> update(@ModelAttribute TireBrandRequest tireBrandRequest,
                                    @PathVariable Long id) throws ServiceException {
        return buildResponse(tireBrandService.update(tireBrandRequest, id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws ServiceException {
        tireBrandService.delete(id);
        return buildSuccessResponse(SuccessResponse.builder().message("Марка шины успешно удалена").build());
    }
}
