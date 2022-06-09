package net.alterapp.altercar.model.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.alterapp.altercar.model.entity.AccountEntity;
import net.alterapp.altercar.model.entity.OrderTireHistoryEntity;
import net.alterapp.altercar.model.entity.PartnerEntity;
import net.alterapp.altercar.model.enums.OrderStatusEnum;
import net.alterapp.altercar.model.enums.OrderTypeEnum;

import java.util.Date;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createdAt;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;
    private Double sum;
    private PartnerEntity partner;
    private Integer amount;
    private AccountEntity user;
    private OrderStatusEnum status;
    private OrderTypeEnum type;
    private String comment;
    private AccountEntity manager;
    private List<OrderTireHistoryEntity> tireHistories;

}
