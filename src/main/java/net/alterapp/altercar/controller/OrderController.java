package net.alterapp.altercar.controller;

import lombok.AllArgsConstructor;
import net.alterapp.altercar.model.requests.TireOrderRequest;
import net.alterapp.altercar.model.responses.errors.ServiceException;
import net.alterapp.altercar.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/order")
public class OrderController extends BaseController {

    private OrderService orderService;


    @PostMapping(value = "/tire")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> create(@RequestBody TireOrderRequest tireOrderRequest) throws ServiceException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return buildResponse(orderService.createTireOrder(tireOrderRequest, ((User) principal).getUsername()), HttpStatus.OK);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> getAll() throws ServiceException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return buildResponse(orderService.getAll(((User) principal).getUsername()), HttpStatus.OK);
    }


    @PostMapping(value = "/confirm/{id}")
    @PreAuthorize("hasRole('ROLE_PARTNER')")
    public ResponseEntity<?> confirm(@PathVariable Long id) throws ServiceException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return buildResponse(orderService.confirm(id, ((User) principal).getUsername()), HttpStatus.OK);
    }

    @PostMapping(value = "/decline/{id}")
    @PreAuthorize("hasRole('ROLE_PARTNER')")
    public ResponseEntity<?> decline(@PathVariable Long id) throws ServiceException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return buildResponse(orderService.decline(id, ((User) principal).getUsername()), HttpStatus.OK);
    }

}
