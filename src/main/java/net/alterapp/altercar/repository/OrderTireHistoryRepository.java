package net.alterapp.altercar.repository;

import net.alterapp.altercar.model.entity.OrderTireHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderTireHistoryRepository extends JpaRepository<OrderTireHistoryEntity, Long> {


    List<OrderTireHistoryEntity> findAllByDeletedAtIsNull();

    List<OrderTireHistoryEntity> findAllByOrder_Id(Long id);

}
