package net.alterapp.altercar.repository;

import net.alterapp.altercar.model.entity.TireBrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TireBrandRepository extends JpaRepository<TireBrandEntity, Long> {

    Optional<TireBrandEntity> findByName(String name);

    List<TireBrandEntity> findAllByDeletedAtIsNull();

}
