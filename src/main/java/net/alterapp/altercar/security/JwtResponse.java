package net.alterapp.altercar.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import net.alterapp.altercar.model.enums.RoleEnum;

@Data
@Builder
@AllArgsConstructor
public class JwtResponse {

  private String token;
  private String refreshToken;
  private RoleEnum role;

}
