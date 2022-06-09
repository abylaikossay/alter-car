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
@Table(name = "family")
@SequenceGenerator(
        name = "seq",
        sequenceName = "s_family",
        initialValue = 1,
        allocationSize = 1)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Family table")
public class FamilyEntity extends AuditModel {


  @ApiModelProperty(notes = "Data of family Parent member")
  @ManyToOne
  private AccountEntity familyParent;

}

