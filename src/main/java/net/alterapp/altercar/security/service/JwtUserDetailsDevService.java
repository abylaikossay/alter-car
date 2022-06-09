package net.alterapp.altercar.security.service;

import net.alterapp.altercar.model.enums.RoleEnum;
import net.alterapp.altercar.model.entity.AccountEntity;
import net.alterapp.altercar.model.entity.RoleEntity;
import net.alterapp.altercar.model.responses.errors.ErrorCode;
import net.alterapp.altercar.model.responses.errors.ServiceException;
import net.alterapp.altercar.repository.AccountRepository;
import net.alterapp.altercar.repository.RoleRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

public class JwtUserDetailsDevService extends JwtUserDetailsService {

  private RoleRepository roleRepository;
  private AccountRepository accountRepository;
  // dev-invest
  private static final String DEV_PASSWORD = "$2a$12$HkPSVg1V73TSgTwEpNXWBuqweQOGnUjjSsP1y1Klo4sH33ezkpsZy";

  public JwtUserDetailsDevService(RoleRepository roleRepository, AccountRepository repository) {
    super(repository);
    this.roleRepository = roleRepository;
    this.accountRepository = repository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    if (Strings.isBlank(username)) {
      throwNotFoundException(username);
    }

    if (username.startsWith("dev-") && accountRepository.findByUsername(username) == null) {
      //role1-role2-role3...
      String roleString = username.replaceAll("dev-", "");

      Set<SimpleGrantedAuthority> authorities = new HashSet<>();
      authorities.add(new SimpleGrantedAuthority(RoleEnum.ROLE_BASIC.name()));

      AccountEntity account = AccountEntity.builder()
          .username(username)
          .password(DEV_PASSWORD)
          .role(getRoleEntity(roleString))
          .build();

      accountRepository.save(account);
    }

    return super.loadUserByUsername(username);
  }

  private RoleEntity getRoleEntity(String roleString) {
    RoleEnum roleEnum2 = RoleEnum.ROLE_BASIC;
    for (String role : Arrays.asList(roleString.split("-"))) {
      RoleEnum roleEnum = RoleEnum.parseOrNull(role);
      if (roleEnum != null) {
        roleEnum2 = roleEnum;
      }
    }
    return roleRepository.findById(roleEnum2).orElseThrow(()-> ServiceException.builder().message("Такой роли не существует")
            .errorCode(ErrorCode.INVALID_ROLE)
            .httpStatus(HttpStatus.BAD_REQUEST)
            .build());
  }

  private Set<RoleEntity> getRoles(String roleString) {
    List<RoleEntity> roles = roleRepository.findAll();

    return getRoleEnums(roleString).stream().map(r -> roles.stream().filter(rr -> rr.getId().equals(r)).findFirst())
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toSet());
  }

  private ArrayList<RoleEnum> getRoleEnums(String roleString) {
    ArrayList<RoleEnum> roles = new ArrayList<>();
    Arrays.asList(roleString.split("-")).forEach(role -> {
      RoleEnum roleEnum = RoleEnum.parseOrNull(role);
      if (roleEnum != null) {
        roles.add(roleEnum);
      }
    });
    return roles;
  }

}
