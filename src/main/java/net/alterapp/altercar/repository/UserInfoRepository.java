package net.alterapp.altercar.repository;

import net.alterapp.altercar.model.entity.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfoEntity, Long> {

  List<UserInfoEntity> findAll();

  Optional<UserInfoEntity> findByAccount_Id(Long accountId);

  List<UserInfoEntity> findAllByFamily_Id(Long id);

}
