package net.alterapp.altercar.service;

import net.alterapp.altercar.model.account.*;
import net.alterapp.altercar.model.role.RoleRecord;

import java.util.List;

public interface AdminService {

  AccountEditResponse createAccount(AccountEditRequest request);

  void blockAccount(Long accountId);

  void unblockAccount(Long accountId);

  List<RoleRecord> getAvailableRoles(Long accountId);

  void updateRoles(Long accountId, AccountUpdateRoleRequest request);

}
