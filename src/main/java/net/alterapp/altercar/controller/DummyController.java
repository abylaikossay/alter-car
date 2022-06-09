package net.alterapp.altercar.controller;

import net.alterapp.altercar.config.api.ApiError;
import net.alterapp.altercar.config.api.ApiSubError;
import net.alterapp.altercar.config.api.ApiValidationError;
import net.alterapp.altercar.security.UnauthorizedException;
import net.alterapp.altercar.service.ClientAttributesHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;

@RestController
@RequestMapping(value = "/api/dummy")
@RequiredArgsConstructor
public class DummyController {

  private final ClientAttributesHelper helper;

  @GetMapping
  public ResponseEntity<String> hello() {
    return ResponseEntity.ok("Hello!");
  }

  @GetMapping(value = "/context")
  public ResponseEntity<Object> lang() {
    return ResponseEntity.ok(helper.getRequestContext());
  }

  @GetMapping(value = "/error")
  public ResponseEntity<String> error() {
    throw new RuntimeException("Dummy exception!");
  }

  @GetMapping(value = "/validation")
  public ResponseEntity<Object> errorValidation() {
    ArrayList<ApiSubError> subErrors = new ArrayList<>();

    subErrors.add(
        ApiValidationError.builder()
            .field("username")
            .message("Must be valid email(Localized message)")
            .rejectedValue("example")
            .build()
    );
    subErrors.add(
        ApiValidationError.builder()
            .field("password")
            .message("Must not be empty(Localized message)")
            .rejectedValue("null")
            .build()
    );

    ApiError apiError = ApiError.builder()
        .timestamp(LocalDateTime.now())
        .requestId(helper.getRequestId())
        .status(HttpStatus.BAD_REQUEST)
        .subErrors(subErrors)
        .message("Bad request!")
        .debugMessage("Here would be some debug message")
        .build();

    return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
  }

  @GetMapping(value = "/unauthorized")
  public ResponseEntity<String> unauthorized() {
    throw new UnauthorizedException(new InsufficientAuthenticationException("Bad credentials"));
  }

}
