package net.alterapp.altercar.model.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.alterapp.altercar.model.entity.audits.AuditModel;
import net.alterapp.altercar.model.enums.OrderStatusEnum;
import net.alterapp.altercar.model.enums.OrderTypeEnum;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "orders")
@SequenceGenerator(
        name = "seq",
        sequenceName = "s_orders",
        initialValue = 1,
        allocationSize = 1)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Orders table")
public class OrderEntity extends AuditModel {

  @ApiModelProperty(notes = "Тип транзакции")
  @Enumerated(value = EnumType.STRING)
  private OrderTypeEnum type;

  @ApiModelProperty(notes = "Пользователь который сделал заказ")
  @ManyToOne
  private AccountEntity account;

  @ApiModelProperty(notes = "Партнер где сделал заказ")
  @ManyToOne
  private PartnerEntity partner;

  @ApiModelProperty(notes = "Сотрудник который сделал заказ")
  @ManyToOne
  private AccountEntity manager;

  @ApiModelProperty(notes = "The amount of products in purchase")
  @Column(name = "amount")
  private Integer amount = 1;

  @ApiModelProperty(notes = "Sum of all products if purchase")
  @Column(name = "sum")
  private Double sum;

  @ApiModelProperty(notes = "Status of transaction")
  @Enumerated(value = EnumType.STRING)
  private OrderStatusEnum status = OrderStatusEnum.PROCESS;

  @ApiModelProperty(notes = "Comment about transaction")
  @Column(name = "comment")
  private String comment;

}

