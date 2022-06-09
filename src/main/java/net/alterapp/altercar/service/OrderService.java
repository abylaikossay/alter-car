package net.alterapp.altercar.service;


import net.alterapp.altercar.model.entity.OrderEntity;
import net.alterapp.altercar.model.requests.TireOrderRequest;
import net.alterapp.altercar.model.responses.OrderResponse;

import java.util.List;

public interface OrderService {


    OrderEntity createTireOrder(TireOrderRequest tireOrderRequest, String userName);

    OrderEntity getById(Long id);

    OrderEntity confirm(Long id, String username);

    OrderEntity decline(Long id, String username);

    List<OrderResponse> getAll(String username);
}
