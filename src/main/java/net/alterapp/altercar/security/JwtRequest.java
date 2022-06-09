package net.alterapp.altercar.security;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class JwtRequest {

  @NotBlank(message = "{validation.notempty}")
  private String username;
  @NotBlank(message = "{validation.notempty}")
  private String password;
  private String refreshToken;

}
