package net.alterapp.altercar.repository;

import net.alterapp.altercar.model.entity.TireEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TireRepository extends JpaRepository<TireEntity, Long> {


    Optional<TireEntity> findByName(String name);

    List<TireEntity> findAllByDeletedAtIsNullAndPartner_Id(Long partnerId);
}
