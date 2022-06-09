package net.alterapp.altercar.model.account;

import net.alterapp.altercar.model.role.RoleRecord;
import net.alterapp.altercar.model.userinfo.UserInfo;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Account {

  public Long id;

  public RoleRecord role;

  public UserInfo userInfo;

}
