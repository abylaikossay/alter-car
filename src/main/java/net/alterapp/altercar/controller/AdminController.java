package net.alterapp.altercar.controller;

import net.alterapp.altercar.model.account.Account;
import net.alterapp.altercar.model.account.AccountEditRequest;
import net.alterapp.altercar.model.account.AccountEditResponse;
import net.alterapp.altercar.model.account.AccountUpdateRoleRequest;
import net.alterapp.altercar.model.role.RoleRecord;
import net.alterapp.altercar.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {

  private final AdminService service;

  @GetMapping
  public ResponseEntity<String> ok() {
    return ResponseEntity.ok("OK!");
  }

  @PostMapping("/account/create")
  public ResponseEntity<AccountEditResponse> createAccount(@Valid @RequestBody AccountEditRequest request) {
    return ResponseEntity.ok(service.createAccount(request));
  }

  @GetMapping("/account/{accountId}/roles/available")
  public ResponseEntity<List<RoleRecord>> getAvailableRoles(@PathVariable Long accountId) {
    return ResponseEntity.ok(service.getAvailableRoles(accountId));
  }

  @PutMapping("/account/{accountId}/roles")
  public void blockAccount(@PathVariable Long accountId, @Valid @RequestBody AccountUpdateRoleRequest request) {
    service.updateRoles(accountId, request);
  }

  @PutMapping("/account/{accountId}/block")
  public void blockAccount(@PathVariable Long accountId) {
    service.blockAccount(accountId);
  }

  @PutMapping("/account/{accountId}/unblock")
  public void unblockAccount(@PathVariable Long accountId) {
    service.unblockAccount(accountId);
  }

}
