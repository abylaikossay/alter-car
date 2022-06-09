package net.alterapp.altercar.model.enums;

import org.apache.logging.log4j.util.Strings;

public enum RoleEnum {
  ROLE_SYSTEM,
  ROLE_ADMIN,
  ROLE_BASIC,
  ROLE_USER,
  ROLE_PARTNER;

  public static RoleEnum parseOrNull(String value) {
    if (Strings.isBlank(value)) {
      return null;
    }

    try {
      return RoleEnum.valueOf(value.toUpperCase());
    } catch (Exception e) {
      return null;
    }
  }
}
