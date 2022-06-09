package net.alterapp.altercar.service.impl;

import lombok.RequiredArgsConstructor;
import net.alterapp.altercar.model.entity.*;
import net.alterapp.altercar.model.enums.OrderStatusEnum;
import net.alterapp.altercar.model.enums.OrderTypeEnum;
import net.alterapp.altercar.model.requests.TireOrderRequest;
import net.alterapp.altercar.model.responses.OrderResponse;
import net.alterapp.altercar.model.responses.errors.ErrorCode;
import net.alterapp.altercar.model.responses.errors.ServiceException;
import net.alterapp.altercar.repository.OrderRepository;
import net.alterapp.altercar.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderTireHistoryService orderTireHistoryService;
    private final AccountService accountService;
    private final UserInfoService userInfoService;


    @Override
    @Transactional
    public OrderEntity createTireOrder(TireOrderRequest tireOrderRequest, String userName) {
        AccountEntity accountEntity = accountService.getByUsername(userName);
        OrderEntity orderEntity = new OrderEntity();
        List<OrderTireHistoryEntity> orderTireHistories = orderTireHistoryService.createTireOrder(tireOrderRequest);
        double sum = 0.0;
        int amount = 0;
        for (OrderTireHistoryEntity orderTireHistory : orderTireHistories) {
            sum += orderTireHistory.getTire().getPrice() * orderTireHistory.getAmount();
            amount += orderTireHistory.getAmount();
        }
        orderEntity.setAccount(accountEntity);
        orderEntity.setComment(tireOrderRequest.getComment());
        orderEntity.setAmount(amount);
        orderEntity.setSum(sum);
        orderEntity.setStatus(OrderStatusEnum.PROCESS);
        orderEntity.setType(OrderTypeEnum.TIRE);
        orderEntity.setPartner(orderTireHistories.get(0).getTire().getPartner());
        orderRepository.save(orderEntity);
        orderTireHistories.forEach((e) -> e.setOrder(orderEntity));
        orderTireHistoryService.saveAll(orderTireHistories);
//        notificate.sendNotification(order);
        return orderEntity;
    }


    @Override
    public OrderEntity getById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No Order Brand Found! " + id));
    }

    @Override
    public OrderEntity confirm(Long id, String username) {
        AccountEntity accountEntity = accountService.getByUsername(username);
        OrderEntity orderEntity = getById(id);
        orderEntity.setStatus(OrderStatusEnum.SUCCESS);
        orderEntity.setManager(accountEntity);
        orderEntity.setUpdatedAt(new Date());
//        notificate.confirm(order);
        return orderRepository.save(orderEntity);
    }

    @Override
    public OrderEntity decline(Long id, String username) {
        AccountEntity accountEntity = accountService.getByUsername(username);
        OrderEntity orderEntity = getById(id);
        orderEntity.setStatus(OrderStatusEnum.DECLINED);
        orderEntity.setManager(accountEntity);
        orderEntity.setUpdatedAt(new Date());
//        notificate.decline(order);
        return orderRepository.save(orderEntity);
    }

    @Override
    public List<OrderResponse> getAll(String username) {
        List<OrderResponse> orderResponses = new ArrayList<>();
        AccountEntity accountEntity = accountService.getByUsername(username);
        UserInfoEntity userInfoEntity = userInfoService.getByUserId(accountEntity.getId());
        List<OrderEntity> orderEntities = orderRepository.findAllByDeletedAtIsNullAndPartner_Id(userInfoEntity.getPartner().getId());
        orderEntities.forEach( orderEntity -> {
            List<OrderTireHistoryEntity> orderTireHistories = orderTireHistoryService.getByOrderId(orderEntity.getId());
            OrderResponse orderResponse = OrderResponse.builder()
                    .id(orderEntity.getId())
                    .createdAt(orderEntity.getCreatedAt())
                    .updatedAt(orderEntity.getUpdatedAt())
                    .amount(orderEntity.getAmount())
                    .comment(orderEntity.getComment())
                    .manager(orderEntity.getManager())
                    .partner(orderEntity.getPartner())
                    .status(orderEntity.getStatus())
                    .sum(orderEntity.getSum())
                    .type(orderEntity.getType())
                    .user(orderEntity.getAccount())
                    .tireHistories(orderTireHistories)
                    .build();
            orderResponses.add(orderResponse);
        });
        return orderResponses;
    }

}
