package net.alterapp.altercar.controller;

import lombok.RequiredArgsConstructor;
import net.alterapp.altercar.model.responses.success.SuccessResponse;
import net.alterapp.altercar.service.FamilyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/family")
public class FamilyController extends BaseController {

    private final FamilyService familyService;

//    @GetMapping()
//    public ResponseEntity<?> getMyFamily() {
//      Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//      return buildResponse(familyService.getMyFamily(((User) principal).getUsername()), HttpStatus.OK);
//    }

    @PostMapping("/invite")
    public ResponseEntity<?> sendInvite(@RequestParam Long userId) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        familyService.sendInvite(((User) principal).getUsername(), userId);
        return buildSuccessResponse(SuccessResponse.builder().message("Успешно зарегистрирован!").build());
    }

    @PostMapping("/create")
    public ResponseEntity<?> createFamily() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return buildResponse(familyService.createFamily(((User) principal).getUsername()), HttpStatus.OK);
    }

}
