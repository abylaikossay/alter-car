package net.alterapp.altercar.repository;

import net.alterapp.altercar.model.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

  AccountEntity findByUsername(String username);

  List<AccountEntity> findAllByIdIn(List<Long> accountIds);
}
