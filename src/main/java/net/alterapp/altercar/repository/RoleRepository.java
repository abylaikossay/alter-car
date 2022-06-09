package net.alterapp.altercar.repository;

import net.alterapp.altercar.model.enums.RoleEnum;
import net.alterapp.altercar.model.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, RoleEnum> {

}
