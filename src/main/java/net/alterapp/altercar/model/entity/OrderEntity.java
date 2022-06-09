package net.alterapp.altercar.model.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.alterapp.altercar.model.entity.audits.AuditModel;
import net.alterapp.altercar.model.enums.OrderTypeEnum;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "order")
@SequenceGenerator(
        name = "seq",
        sequenceName = "s_order",
        initialValue = 1,
        allocationSize = 1)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Orders table")
public class OrderEntity extends AuditModel {

  @ApiModelProperty(notes = "Наименование")
  @Column(name = "name")
  private String name;

  @ApiModelProperty(notes = "Тип транзакции")
  @Enumerated(value = EnumType.STRING)
  private OrderTypeEnum type;

  @ApiModelProperty(notes = "Пользователь который сделал заказ")
  @ManyToOne
  private AccountEntity account;



  @ApiModelProperty(notes = "Логотив бренда шин")
  @Column(name = "logo_url")
  private String logoUrl;



}

