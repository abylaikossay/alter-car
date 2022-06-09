package net.alterapp.altercar.security;

import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

@Getter
public class UnauthorizedException extends RuntimeException {

  private final AuthenticationException authException;

  public UnauthorizedException(AuthenticationException authException) {
    super(authException.getClass() + ", " + authException.getLocalizedMessage());
    this.authException = authException;
  }

}
