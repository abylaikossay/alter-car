package net.alterapp.altercar.controller;

import lombok.AllArgsConstructor;
import net.alterapp.altercar.model.requests.TireRequest;
import net.alterapp.altercar.model.responses.errors.ServiceException;
import net.alterapp.altercar.model.responses.success.SuccessResponse;
import net.alterapp.altercar.service.TireService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/order")
public class OrderController extends BaseController {


    @PostMapping(value = "/tire")
    public ResponseEntity<?> create(@RequestBody OrderRequest orderRequest) throws ServiceException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return buildResponse(tireService.create(tireRequest, ((User) principal).getUsername()), HttpStatus.OK);
    }

}
