package net.alterapp.altercar.model.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.alterapp.altercar.model.enums.GenderEnum;
import net.alterapp.altercar.validation.Patronymic;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRegisterRequest {

  @NotEmpty(message = "{validation.notempty}")
  private String phone;
  private String password;
  private Integer cityId;
  private String name;
  private String surname;
  private Date birthDate;
  private String email;
  private GenderEnum gender;
  private String language;
  private String deviceToken;
  private String platform;
}
