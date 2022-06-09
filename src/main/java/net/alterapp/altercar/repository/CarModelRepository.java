package net.alterapp.altercar.repository;

import net.alterapp.altercar.model.entity.CarModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarModelRepository extends JpaRepository<CarModelEntity, Long> {
    Optional<CarModelEntity> findByName(String name);


    List<CarModelEntity> findAllByDeletedAtIsNull();


}
