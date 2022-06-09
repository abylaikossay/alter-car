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
@RequestMapping(value = "/api/tire")
@PreAuthorize("hasRole('ROLE_PARTNER')")
public class TireController extends BaseController {

    private TireService tireService;


    @PostMapping(value = "/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> create(@ModelAttribute TireRequest tireRequest) throws ServiceException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return buildResponse(tireService.create(tireRequest, ((User) principal).getUsername()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) throws ServiceException {
        return buildResponse(tireService.getById(id), HttpStatus.OK);
    }


    @GetMapping("/all")
    public ResponseEntity<?> getAll(@RequestParam Optional<Integer> page,
                                    @RequestParam Optional<Integer> size,
                                    @RequestParam Optional<String[]> sortBy,
                                    @RequestParam(value = "search") Optional<String> search) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return buildResponse(tireService.getAll(search, page, size, sortBy, ((User) principal).getUsername()), HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> update(@ModelAttribute TireRequest tireRequest,
                                    @PathVariable Long id) throws ServiceException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return buildResponse(tireService.update(tireRequest, id, ((User) principal).getUsername()), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws ServiceException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        tireService.delete(id, ((User) principal).getUsername());
        return buildSuccessResponse(SuccessResponse.builder().message("Шина успешно удалена").build());
    }
}
