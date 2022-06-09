package net.alterapp.altercar.model.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import net.alterapp.altercar.model.entity.audits.AuditModel;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "account")
@SequenceGenerator(
        name = "seq",
        sequenceName = "s_account",
        initialValue = 1,
        allocationSize = 1)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Account table")
public class AccountEntity extends AuditModel {

  @Column(name = "username", unique = true)
  private String username;

  @Column(name = "password")
  private String password;

  @OneToOne(mappedBy = "account", fetch = FetchType.LAZY)
  private UserInfoEntity userInfo;

  @ApiModelProperty(notes = "Identification of role in accounts")
  @ManyToOne
  private RoleEntity role;

}

