package net.alterapp.altercar.model.account;

import net.alterapp.altercar.model.enums.RoleEnum;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class AccountUpdateRoleRequest {

  @NotEmpty(message = "{validation.notempty}")
  public RoleEnum role;

}
