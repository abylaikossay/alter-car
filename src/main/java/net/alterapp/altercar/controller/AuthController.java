package net.alterapp.altercar.controller;

import net.alterapp.altercar.model.account.AccountEditRequest;
import net.alterapp.altercar.model.account.AccountEditResponse;
import net.alterapp.altercar.model.account.AccountRegisterRequest;
import net.alterapp.altercar.model.entity.AccountEntity;
import net.alterapp.altercar.model.responses.errors.ServiceException;
import net.alterapp.altercar.model.responses.success.SuccessResponse;
import net.alterapp.altercar.security.JwtRequest;
import net.alterapp.altercar.security.JwtResponse;
import net.alterapp.altercar.service.AccountService;
import net.alterapp.altercar.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/auth")
public class AuthController extends BaseController {

    private final AuthService authService;
    private final AccountService accountService;

    @PostMapping(value = "/login")
    public JwtResponse getToken(@Valid @RequestBody JwtRequest request) {
        return authService.getToken(request);
    }

    @PostMapping("/account/create")
    public ResponseEntity<AccountEntity> createAccount(@Valid @RequestBody AccountEditRequest request) {
        return ResponseEntity.ok(accountService.create(request));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AccountRegisterRequest registerRequest) throws ServiceException {
        accountService.registerUser(registerRequest);
        return buildSuccessResponse(SuccessResponse.builder().message("Смс с кодом подтверждения успешно отправлен").build());
    }

    @PutMapping("/register/confirm")
    public ResponseEntity<?> confirmRegister(@RequestParam String phone, @RequestParam String registrationCode) {
        accountService.confirmRegister(phone, registrationCode);
        return buildSuccessResponse(SuccessResponse.builder().message("Успешно зарегистрирован!").build());
    }

    @PutMapping("/register/resend-sms")
    public ResponseEntity<?> resendSmsToConfirm(@RequestParam String phone) {
        accountService.resendSmsToConfirm(phone);
        return buildSuccessResponse(SuccessResponse.builder().message("Смс успешно отправлено!").build());
    }

    @PostMapping(value = "/token/refresh")
    public JwtResponse refreshToken(@RequestBody JwtRequest request) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return authService.refreshToken(request.getRefreshToken());
    }

}
