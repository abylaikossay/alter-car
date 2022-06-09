package net.alterapp.altercar.events.listener;

import net.alterapp.altercar.events.UpdateAccountPasswordEvent;
import net.alterapp.altercar.email.EmailService;
import net.alterapp.altercar.model.entity.AccountEntity;
import net.alterapp.altercar.security.PasswordGenerator;
import net.alterapp.altercar.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
public class UpdateAccountPasswordEventListener implements ApplicationListener<UpdateAccountPasswordEvent> {

  private final EmailService emailService;
  private final PasswordEncoder passwordEncoder;
  private final PasswordGenerator passwordGenerator;
  private final AccountService accountService;

  @Override
  @Transactional
  public void onApplicationEvent(UpdateAccountPasswordEvent event) {
    Long accountId = (Long) event.getSource();

    String newPassword = passwordGenerator.generatePassword();

    AccountEntity account = accountService.getById(accountId);
    account.setPassword(passwordEncoder.encode(newPassword));
    accountService.save(account);

    // TODO: 27.11.2021 Implement
    emailService.send(account.getUsername(), "Добро пожаловать!", newPassword);
  }

}
