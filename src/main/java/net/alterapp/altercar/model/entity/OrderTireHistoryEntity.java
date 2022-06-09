package net.alterapp.altercar.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "order_tire_history")
@SequenceGenerator(
        name = "seq",
        sequenceName = "s_order_tire_history",
        initialValue = 1,
        allocationSize = 1)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Order Tire History table")
public class OrderTireHistoryEntity extends AuditModel {


  @ApiModelProperty(notes = "Какая шины была куплена")
  @ManyToOne
  private TireEntity tire;

  @ApiModelProperty(notes = "Количество шин в покупке")
  @Column(name = "amount")
  private Integer amount;

  @ApiModelProperty(notes = "Returned product or not?")
  @Column(name = "returned")
  private Boolean returned = false;

  @ApiModelProperty(notes = "Returned amount of product or not?")
  @Column(name = "returned_amount")
  private int returnedAmount = 0;

  @ApiModelProperty(notes = "Check of the statement:  Was purchase is  returnable or Not?")
  @Column(name="returnable")
  private Boolean returnable;

  @ApiModelProperty(notes = "К какому заказу относится")
  @ManyToOne
  @JsonIgnore
  private OrderEntity order;
}

