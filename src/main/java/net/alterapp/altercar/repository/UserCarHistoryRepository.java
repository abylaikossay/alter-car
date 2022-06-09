package net.alterapp.altercar.repository;

import net.alterapp.altercar.model.entity.UserCarHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCarHistoryRepository extends JpaRepository<UserCarHistoryEntity, Long> {

}
