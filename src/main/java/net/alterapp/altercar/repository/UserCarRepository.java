package net.alterapp.altercar.repository;

import net.alterapp.altercar.model.entity.UserCarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCarRepository extends JpaRepository<UserCarEntity, Long> {

    List<UserCarEntity> findAllByAccount_Id(Long id);

    List<UserCarEntity> findAllByAccount_IdIn(List<Long> accountIds);
}
