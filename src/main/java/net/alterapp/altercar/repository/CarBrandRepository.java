package net.alterapp.altercar.repository;

import net.alterapp.altercar.model.entity.CarBrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarBrandRepository extends JpaRepository<CarBrandEntity, Long> {

    Optional<CarBrandEntity> findByName(String name);

    List<CarBrandEntity> findAllByDeletedAtIsNull();

}
