package net.alterapp.altercar.model.role;

import net.alterapp.altercar.model.enums.RoleEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleRecord {

  public RoleEnum id;
  public String title;

}
