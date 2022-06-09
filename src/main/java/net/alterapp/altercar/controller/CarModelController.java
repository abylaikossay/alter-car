package net.alterapp.altercar.controller;

import lombok.RequiredArgsConstructor;
import net.alterapp.altercar.model.requests.CarModelRequest;
import net.alterapp.altercar.model.responses.errors.ServiceException;
import net.alterapp.altercar.model.responses.success.SuccessResponse;
import net.alterapp.altercar.service.CarModelService;
import net.alterapp.altercar.service.FamilyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/car-model")
public class CarModelController extends BaseController {

    private final CarModelService carModelService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CarModelRequest carBrandRequest) throws ServiceException {
        carModelService.create(carBrandRequest);
        return buildSuccessResponse(SuccessResponse.builder().message("Марка машины успешно создана").build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) throws ServiceException {
        return buildResponse(carModelService.getById(id), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<?> getModelList() throws ServiceException {
        return buildResponse(carModelService.getList(), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody CarModelRequest carBrandRequest,
                                    @PathVariable Long id) throws ServiceException {
        carModelService.update(carBrandRequest, id);
        return buildSuccessResponse(SuccessResponse.builder().message("Марка машины успешно обновлена").build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws ServiceException {
        carModelService.delete(id);
        return buildSuccessResponse(SuccessResponse.builder().message("Марка машины успешно обновлена").build());
    }

}
