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
@Table(name = "user_car")
@SequenceGenerator(
        name = "seq",
        sequenceName = "s_user_car",
        initialValue = 1,
        allocationSize = 1)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "User Car table")
public class UserCarEntity extends AuditModel {

  @ApiModelProperty(notes = "Account table id")
  @ManyToOne
  private AccountEntity account;

  @ApiModelProperty(notes = "Car brand table id")
  @ManyToOne
  private CarBrandEntity carBrand;

  @ApiModelProperty(notes = "Car brand table id")
  @ManyToOne
  private CarModelEntity carModel;

  @ApiModelProperty(notes = "Car year")
  @Column(name = "car_year")
  private Integer carYear;

  @ApiModelProperty(notes = "Объем двигателя")
  @Column(name = "engine_volume")
  private Double engineVolume;

  @ApiModelProperty(notes = "VIN код машины")
  @Column(name = "vin_code")
  private String vinCode;

  @ApiModelProperty(notes = "Цвет машины")
  @Column(name = "color")
  private String color;
}

