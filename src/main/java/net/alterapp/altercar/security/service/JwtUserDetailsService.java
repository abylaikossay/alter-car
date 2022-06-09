package net.alterapp.altercar.security.service;

import net.alterapp.altercar.model.entity.AccountEntity;
import net.alterapp.altercar.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

  private final AccountRepository repository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    if (Strings.isBlank(username)) {
      throwNotFoundException(username);
    }

    AccountEntity account = repository.findByUsername(username);
    if (account == null) {
      throwNotFoundException(username);
    }

    // TODO: 22.11.2021 check for old token

    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
    if (account.getRole() != null) {
      authorities.add(new SimpleGrantedAuthority(String.valueOf(account.getRole().getId())));
    }

    return new User(account.getUsername(), account.getPassword(), authorities);
  }

  protected void throwNotFoundException(String username) {
    throw new UsernameNotFoundException("Account not found with username: " + username);
  }

}
