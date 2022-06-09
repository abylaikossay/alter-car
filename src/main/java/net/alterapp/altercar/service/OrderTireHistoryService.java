package net.alterapp.altercar.service;


import net.alterapp.altercar.model.entity.OrderTireHistoryEntity;
import net.alterapp.altercar.model.requests.TireOrderRequest;

import java.util.List;

public interface OrderTireHistoryService {


    OrderTireHistoryEntity create();

    OrderTireHistoryEntity getById(Long id);

    List<OrderTireHistoryEntity> createTireOrder(TireOrderRequest tireOrderRequest);

    List<OrderTireHistoryEntity> getByOrderId(Long orderId);

    void saveAll(List<OrderTireHistoryEntity> orderTireHistories);
}
