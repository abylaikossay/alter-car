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
@Table(name = "car_model")
@SequenceGenerator(
        name = "seq",
        sequenceName = "s_car_model",
        initialValue = 1,
        allocationSize = 1)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Car Brand table")
public class CarModelEntity extends AuditModel {

  @ApiModelProperty(notes = "Name of carbrand")
  @Column(name = "name")
  private String name;

  @ApiModelProperty(notes = "Car brand")
  @ManyToOne
  private CarBrandEntity carBrand;

}

