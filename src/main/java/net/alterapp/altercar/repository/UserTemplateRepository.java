package net.alterapp.altercar.repository;

import net.alterapp.altercar.model.entity.UserTemplateEntity;
import net.alterapp.altercar.model.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTemplateRepository extends JpaRepository<UserTemplateEntity, RoleEnum> {

    Optional<UserTemplateEntity> findByUsername(String username);

    UserTemplateEntity findFirstByUsername(String username);
}
