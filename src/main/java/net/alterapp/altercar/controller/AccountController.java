package net.alterapp.altercar.controller;

import lombok.RequiredArgsConstructor;
import net.alterapp.altercar.model.responses.success.SuccessResponse;
import net.alterapp.altercar.service.AccountService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/account")
public class AccountController extends BaseController {

    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<String> ok() {
        return ResponseEntity.ok("OK!");
    }

//  @PostMapping("/register")
//  public ResponseEntity<?> register(@RequestBody AccountRegisterRequest registerRequest) throws ServiceException {
//    accountService.registerUser(registerRequest);
//    return buildSuccessResponse(SuccessResponse.builder().message("Смс с кодом подтверждения успешно отправлен").build());
//  }

    @PutMapping(value = "/profile/avatar", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> changeUserAvatar(@ModelAttribute MultipartFile image) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        accountService.changeUserAvatar(image, ((User) principal).getUsername());
        return buildSuccessResponse(SuccessResponse.builder().message("Аватарка пользователя успешно изменен").build());
    }

}
