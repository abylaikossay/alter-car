package net.alterapp.altercar.repository;

import net.alterapp.altercar.model.entity.FamilyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FamilyRepository extends JpaRepository<FamilyEntity, Long> {
  Optional<FamilyEntity> findByFamilyParent_Id(Long userId);

}
