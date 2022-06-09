package net.alterapp.altercar.model.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.alterapp.altercar.model.entity.audits.AuditModel;
import net.alterapp.altercar.model.enums.TireSeasonEnum;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "tire")
@SequenceGenerator(
        name = "seq",
        sequenceName = "s_tire",
        initialValue = 1,
        allocationSize = 1)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Tire table")
public class TireEntity extends AuditModel {

    @ApiModelProperty(notes = "Диаметр шины")
    @Column(name = "diameter")
    private Integer diameter;

    @ApiModelProperty(notes = "Сезонность")
    @Enumerated(value = EnumType.STRING)
    private TireSeasonEnum season;

    @ApiModelProperty(notes = "Ширина шины")
    @Column(name = "width")
    private Integer width;

    @ApiModelProperty(notes = "Высота шины")
    @Column(name = "height")
    private Integer height;

    @ApiModelProperty(notes = "Цена шины")
    @Column(name = "price")
    private Double price;

    @ApiModelProperty(notes = "Наименование шины")
    @Column(name = "name")
    private String name;

    @ApiModelProperty(notes = "Бренд")
    @ManyToOne
    private TireBrandEntity brand;

    @ApiModelProperty(notes = "Качество")
    @Column(name = "quality")
    private String quality;

    @ApiModelProperty(notes = "Количество шины")
    @Column(name = "amount")
    private Integer amount;

    @ApiModelProperty(notes = "Фото шины")
    @Column(name = "photo_url")
    private String photoUrl;

    @ApiModelProperty(notes = "Identification of partner of tire")
    @ManyToOne
    private PartnerEntity partner;
}

