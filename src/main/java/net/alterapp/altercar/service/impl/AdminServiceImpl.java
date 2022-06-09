package net.alterapp.altercar.service.impl;

import io.jsonwebtoken.lang.Assert;
import net.alterapp.altercar.model.account.*;
import net.alterapp.altercar.model.role.RoleRecord;
import net.alterapp.altercar.service.AccountService;
import net.alterapp.altercar.service.AdminService;
import net.alterapp.altercar.service.RoleDataService;
import net.alterapp.altercar.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

  private final RoleDataService roleDataService;
  private final AccountService accountService;
  private final ApplicationEventPublisher applicationEventPublisher;


  @Override
  public AccountEditResponse createAccount(AccountEditRequest request) {
    Assert.notNull(request, "Argument cannot be null");

    request.id = accountService.create(request).getId();

//    applicationEventPublisher.publishEvent(new UpdateAccountPasswordEvent(request.id));

    return AccountEditResponse.builder()
        .account(request)
        .build();
  }

  @Override
  public void blockAccount(Long accountId) {
    throw new UnsupportedOperationException("not implemented");
  }

  @Override
  public void unblockAccount(Long accountId) {
    throw new UnsupportedOperationException("not implemented");
  }

  @Override
  public List<RoleRecord> getAvailableRoles(Long accountId) {
    Assert.notNull(accountId, "Argument 'accountId' cannot be null");
    return roleDataService.getRecords();
  }

  @Override
  public void updateRoles(Long accountId, AccountUpdateRoleRequest request) {
    accountService.updateRoles(accountId, request);
  }

}
