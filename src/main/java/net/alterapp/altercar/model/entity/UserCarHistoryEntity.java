package net.alterapp.altercar.model.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.alterapp.altercar.model.entity.audits.AuditModel;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "user_car_history")
@SequenceGenerator(
        name = "seq",
        sequenceName = "s_user_car_history",
        initialValue = 1,
        allocationSize = 1)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "User Car History table")
public class UserCarHistoryEntity extends AuditModel {

  @ApiModelProperty(notes = "Previous Account table id")
  @ManyToOne
  private AccountEntity previousAccount;

  @ApiModelProperty(notes = "Current Account table id")
  @ManyToOne
  private AccountEntity currentAccount;

  @ApiModelProperty(notes = "User Car brand table id")
  @ManyToOne
  private UserCarEntity userCar;

}

