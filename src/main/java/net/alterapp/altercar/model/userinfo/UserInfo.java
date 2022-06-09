package net.alterapp.altercar.model.userinfo;

import lombok.Builder;
import lombok.Data;
import net.alterapp.altercar.model.enums.GenderEnum;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

@Data
@Builder
public class UserInfo {

  public Long id;
  public String username;
  public String surname;
  public String name;
  public String patronymic;
  private Integer cityId;
  private Date birthDate;
  private String email;

  @Enumerated(value = EnumType.STRING)
  private GenderEnum gender;

  private String language;
  private String deviceToken;
  private String platform;
  private String avatarUrl;
}
