package net.alterapp.altercar.model.userinfo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfoRecord {

  public Long id;

  public String username;

  public String surname;

  public String name;

  public String patronymic;

}
