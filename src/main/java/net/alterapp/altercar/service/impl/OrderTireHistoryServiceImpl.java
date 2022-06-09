package net.alterapp.altercar.service.impl;

import lombok.RequiredArgsConstructor;
import net.alterapp.altercar.model.entity.OrderTireHistoryEntity;
import net.alterapp.altercar.model.entity.TireEntity;
import net.alterapp.altercar.model.requests.TireOrderRequest;
import net.alterapp.altercar.model.responses.errors.ErrorCode;
import net.alterapp.altercar.model.responses.errors.ServiceException;
import net.alterapp.altercar.repository.OrderTireHistoryRepository;
import net.alterapp.altercar.service.OrderTireHistoryService;
import net.alterapp.altercar.service.TireService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class OrderTireHistoryServiceImpl implements OrderTireHistoryService {

    private final OrderTireHistoryRepository orderTireHistoryRepository;
    private final TireService tireService;


    @Override
    public OrderTireHistoryEntity create() {
        return null;
    }



    @Override
    public OrderTireHistoryEntity getById(Long id) {
        return orderTireHistoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No Partner Brand Found! " + id));
    }

    @Override
    public List<OrderTireHistoryEntity> createTireOrder(TireOrderRequest tireOrderRequest) {
        List<OrderTireHistoryEntity> orderTireHistoryEntities = new ArrayList<>();
        tireOrderRequest.getTireOrderProductRequests().forEach(tireOrderProductRequest -> {
            OrderTireHistoryEntity orderTireHistoryEntity = new OrderTireHistoryEntity();
            TireEntity tireEntity = tireService.getById(tireOrderProductRequest.getTireId());
            checkTireAmount(tireEntity, tireOrderProductRequest.getAmount());
            tireEntity.setAmount(tireEntity.getAmount() - tireOrderProductRequest.getAmount());
            tireService.save(tireEntity);
            orderTireHistoryEntity.setTire(tireEntity);
            orderTireHistoryEntity.setAmount(tireOrderProductRequest.getAmount());
            orderTireHistoryEntities.add(orderTireHistoryEntity);
        });

        return orderTireHistoryEntities;
    }

    @Override
    public List<OrderTireHistoryEntity> getByOrderId(Long orderId) {
        return orderTireHistoryRepository.findAllByOrder_Id(orderId);
    }

    @Override
    public void saveAll(List<OrderTireHistoryEntity> orderTireHistories) {
        orderTireHistoryRepository.saveAll(orderTireHistories);
    }

    public void checkTireAmount(TireEntity tireEntity, Integer ammount) {
        if (ammount > tireEntity.getAmount()) {
            throw ServiceException.builder()
                    .errorCode(ErrorCode.NOT_ALLOWED)
                    .httpStatus(HttpStatus.CONFLICT)
                    .message("Ошибка! Недостаточное количество шин")
                    .build();
        }
    }

}
