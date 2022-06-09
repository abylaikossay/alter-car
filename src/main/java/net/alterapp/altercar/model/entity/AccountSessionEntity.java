package net.alterapp.altercar.model.entity;

import io.swagger.annotations.ApiModel;
import lombok.*;
import net.alterapp.altercar.model.entity.audits.AuditModel;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(name = "account_session")
@SequenceGenerator(
        name = "seq",
        sequenceName = "s_account_session",
        initialValue = 1,
        allocationSize = 1)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Account session table")
public class AccountSessionEntity extends AuditModel {

  @Column(name = "refresh_token")
  private String refreshToken;

  @Column(name = "expiry_date")
  private LocalDateTime expiryDate;

  @ManyToOne
  private AccountEntity account;

}
