package net.alterapp.altercar.repository;

import net.alterapp.altercar.model.entity.PartnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartnerRepository extends JpaRepository<PartnerEntity, Long> {

    Optional<PartnerEntity> findByName(String name);

    List<PartnerEntity> findAllByDeletedAtIsNull();

}
