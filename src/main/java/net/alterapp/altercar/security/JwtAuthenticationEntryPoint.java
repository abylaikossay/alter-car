package net.alterapp.altercar.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.alterapp.altercar.config.api.ApiError;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private final ObjectMapper objectMapper;

  @Override
  @SneakyThrows
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) {
    ApiError error = new ApiError(HttpStatus.UNAUTHORIZED, "Unauthorized request", ex);

    response.setContentType("application/json");
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.getOutputStream().print(objectMapper.writeValueAsString(error));
  }

}
