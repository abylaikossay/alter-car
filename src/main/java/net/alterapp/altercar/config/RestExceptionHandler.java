package net.alterapp.altercar.config;

import net.alterapp.altercar.config.api.ApiError;
import net.alterapp.altercar.config.api.ApiException;
import net.alterapp.altercar.config.api.ApiSubError;
import net.alterapp.altercar.config.api.ApiValidationError;
import net.alterapp.altercar.security.UnauthorizedException;
import net.alterapp.altercar.service.ClientAttributesHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@RequiredArgsConstructor
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  private final ClientAttributesHelper clientAttributesHelper;

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleInternal(Exception ex, WebRequest request) {
    String error = "Internal error";
    log.error(error, ex);
    return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, error, ex));
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<Object> handleUnauthorized(AccessDeniedException ex, WebRequest request) {
    String error = "Access denied!";
    return buildResponseEntity(new ApiError(HttpStatus.FORBIDDEN, error, ex));
  }

  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<Object> handleUnauthorized(UnauthorizedException ex, WebRequest request) {
    String error = "Unauthorized";
    return buildResponseEntity(new ApiError(HttpStatus.UNAUTHORIZED, error, ex.getAuthException()));
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex, WebRequest request) {
    String error = "Entity not found";
    return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, error, ex));
  }

  @ExceptionHandler(MaxUploadSizeExceededException.class)
  public ResponseEntity<Object> handleEntityNotFound(MaxUploadSizeExceededException ex, WebRequest request) {
    String error = "Too large file";
    return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<Object> handleBadCredentials(BadCredentialsException ex, WebRequest request) {
    String error = "Bad username or password";
    return buildResponseEntity(
        ApiError.builder()
            .requestId(clientAttributesHelper.getRequestId())
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.BAD_REQUEST)
            .subErrors(
                List.of(
                    ApiValidationError.builder()
                        .message(error)
                        .field("username")
                        .build(),
                    ApiValidationError.builder()
                        .message(error)
                        .field("password")
                        .build()
                )
            )
            .message("Bad request!")
            .build()
    );
  }

  @ExceptionHandler(ApiException.class)
  public ResponseEntity<Object> handleApiException(ApiException ex, WebRequest request) {
    return buildResponseEntity(
        ApiError.builder()
            .requestId(clientAttributesHelper.getRequestId())
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.BAD_REQUEST)
            .subErrors(ex.getSubErrors())
            .message("Bad request!")
            .build()
    );
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status,
                                                                WebRequest request) {
    String error = "Message not readable";
    return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
  }

//  @Override
//  protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers,
//                                                                       HttpStatus status, WebRequest request) {
//    String error = "Method not allowed";
//    return buildResponseEntity(new ApiError(HttpStatus.METHOD_NOT_ALLOWED, error, ex));
//  }


  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                HttpHeaders headers, HttpStatus status, WebRequest request) {

    List<ApiSubError> validationErrors = ex.getBindingResult().getAllErrors()
        .stream()
        .map((err) -> {
          FieldError fieldErr = (FieldError) err;
          return ApiValidationError.builder()
              .field(fieldErr.getField())
              .rejectedValue(fieldErr.getRejectedValue())
              .message(fieldErr.getDefaultMessage())
              .build();
        }).collect(Collectors.toList());

    return buildResponseEntity(
        ApiError.builder()
            .requestId(clientAttributesHelper.getRequestId())
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.BAD_REQUEST)
            .subErrors(validationErrors)
            .message("Bad request!")
            .build()
    );
  }

  private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
    return new ResponseEntity<>(apiError, apiError.getStatus());
  }

}
