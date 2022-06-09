package net.alterapp.altercar.repository;

import net.alterapp.altercar.model.entity.AccountSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface AccountSessionRepository extends JpaRepository<AccountSessionEntity, Long> {

//  @Transactional
//  void deleteAllByUsername(String username);

  @Transactional
  void deleteAllByAccount_Id(Long accountId);

  AccountSessionEntity findByRefreshToken(String username);

}
