package net.alterapp.altercar.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import net.alterapp.altercar.model.entity.audits.AuditModel;
import net.alterapp.altercar.model.enums.GenderEnum;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@Entity
@Table(name = "user_info")
@SequenceGenerator(
        name = "seq",
        sequenceName = "s_user_info",
        initialValue = 1,
        allocationSize = 1)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "User Info table")
public class UserInfoEntity extends AuditModel {

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

  @Column(name = "avatar_url")
  private String avatarUrl;

  @ToString.Exclude
  @JoinColumn(name = "account_id")
  @JsonIgnore
  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private AccountEntity account;

  @ApiModelProperty(notes = "Data of user family")
  @ManyToOne
  private FamilyEntity family;

  @ApiModelProperty(notes = "Если сотрудник, то какого партнера")
  @ManyToOne
  private PartnerEntity partner;

}
