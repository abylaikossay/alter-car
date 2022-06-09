package net.alterapp.altercar.model.account;

import lombok.Builder;
import net.alterapp.altercar.model.enums.RoleEnum;
import net.alterapp.altercar.validation.Patronymic;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
public class AccountEditRequest {

  public Long id;

  @NotEmpty(message = "{validation.notempty}")
  public String username;

  @NotEmpty(message = "{validation.notempty}")
  public String password;

  @Size(min = 2, message = "{validation.value.moreThan.chars}")
  public String surname;

  @NotEmpty(message = "{validation.notempty}")
  @Size(min = 2, message = "{validation.value.moreThan.chars}")
  public String name;

  @Patronymic(min = 2)
  public String patronymic;

  @Enumerated(value = EnumType.STRING)
  public RoleEnum role;

  public Long partnerId;

}
