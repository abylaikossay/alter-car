package net.alterapp.altercar.model.account;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountEditResponse {

  public AccountEditRequest account;

}
