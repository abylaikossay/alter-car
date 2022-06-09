package net.alterapp.altercar.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import net.alterapp.altercar.model.account.AccountRegisterRequest;
import net.alterapp.altercar.model.requests.CarBrandRequest;
import net.alterapp.altercar.model.responses.errors.ServiceException;
import net.alterapp.altercar.model.responses.success.SuccessResponse;
import net.alterapp.altercar.service.CarBrandService;
import net.alterapp.altercar.service.FamilyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/car-brand")
public class CarBrandController extends BaseController {

    private CarBrandService carBrandService;


    @PostMapping(value = "/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> create(@ModelAttribute CarBrandRequest carBrandRequest) throws ServiceException {
        return buildResponse(carBrandService.create(carBrandRequest), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) throws ServiceException {
        return buildResponse(carBrandService.getById(id), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<?> getBrandList() throws ServiceException {
        return buildResponse(carBrandService.getList(), HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> update(@ModelAttribute CarBrandRequest carBrandRequest,
                                    @PathVariable Long id) throws ServiceException {
        return buildResponse(carBrandService.update(carBrandRequest, id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws ServiceException {
        carBrandService.delete(id);
        return buildSuccessResponse(SuccessResponse.builder().message("Марка машины успешно удалена").build());
    }

//    @PutMapping(value = "/{id}/logo", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//    public ResponseEntity<?> setBrandLogo(@ModelAttribute MultipartFile image,
//                                              @PathVariable Long id) {
//        carBrandService.setBrandLogo(image, id);
//        return buildSuccessResponse(SuccessResponse.builder().message("Логотип бренда успешно изменен").build());
//    }
}
