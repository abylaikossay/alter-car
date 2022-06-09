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
@Table(name = "tire_brand")
@SequenceGenerator(
        name = "seq",
        sequenceName = "s_tire_brand",
        initialValue = 1,
        allocationSize = 1)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Tire Brand table")
public class TireBrandEntity extends AuditModel {

  @ApiModelProperty(notes = "Наименование")
  @Column(name = "name")
  private String name;

  @ApiModelProperty(notes = "Логотив бренда шин")
  @Column(name = "logo_url")
  private String logoUrl;



}

