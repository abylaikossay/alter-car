package net.alterapp.altercar.repository;

import net.alterapp.altercar.model.entity.UserAttemptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface UserAttemptsRepository extends JpaRepository<UserAttemptEntity, Long> {

    @Query(value = "SELECT * from user_attempts WHERE registration_attempts > 1",nativeQuery = true)
    List<UserAttemptEntity> getAllUsers();

    UserAttemptEntity findDistinctByAccountEntity_Id(Long accountEntityId);

    UserAttemptEntity findDistinctByUserTemplate_Id(Long userTemplateId);


}
