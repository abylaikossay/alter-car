package net.alterapp.altercar.model.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.alterapp.altercar.model.entity.audits.AuditModel;
import net.alterapp.altercar.model.enums.GenderEnum;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@Entity
@Table(name = "users_template")
@SequenceGenerator(
        name = "seq",
        sequenceName = "s_users_template",
        initialValue = 1,
        allocationSize = 1)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Users template table")
public class UserTemplateEntity extends AuditModel {

  @Column(name = "username", unique = true)
  private String username;

  @Column(name = "password")
  private String password;

  @Column(name = "surname")
  private String surname;

  @Column(name = "name")
  private String name;

  @Column(name = "patronymic")
  private String patronymic;

  @Column(name = "city_id")
  private Integer cityId;

  @Column(name = "birth_date")
  private Date birthDate;

  @Column(name = "email")
  private String email;

  @Column(name = "gender")
  @Enumerated(value = EnumType.STRING)
  private GenderEnum gender;

  @Column(name = "language")
  private String language;

  @Column(name = "device_token")
  private String deviceToken;

  @Column(name = "platform")
  private String platform;

  @Column(name = "activation_code")
  private String activationCode;

  @ApiModelProperty(notes = "Количество попыток регистрации через смс")
  @Column(name = "sms_attempts")
  private Integer smsAttempts;

  @ApiModelProperty(notes = "Identification of role in accounts")
  @ManyToOne
  private RoleEntity role;


}

