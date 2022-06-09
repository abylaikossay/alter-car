package net.alterapp.altercar.service.impl;

import net.alterapp.altercar.model.enums.RoleEnum;
import net.alterapp.altercar.model.role.RoleRecord;
import net.alterapp.altercar.repository.RoleRepository;
import net.alterapp.altercar.service.RoleDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleDataServiceImpl implements RoleDataService {

  private final RoleRepository repository;

  @Override
  @Transactional
  public List<RoleRecord> getRecords() {
    return repository.findAll()
        .stream()
        .filter(roleEntity -> !roleEntity.getId().equals(RoleEnum.ROLE_SYSTEM))
        .map(roleEntity ->
                 RoleRecord.builder()
                     .id(roleEntity.getId())
                     .title(roleEntity.getName())
                     .build()
        )
        .collect(Collectors.toList());
  }

}
