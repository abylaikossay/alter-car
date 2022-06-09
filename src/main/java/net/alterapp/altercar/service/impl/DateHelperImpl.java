package net.alterapp.altercar.service.impl;

import net.alterapp.altercar.service.DateHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DateHelperImpl implements DateHelper {

  @Override
  public LocalDateTime getNow() {
    return LocalDateTime.now();
  }

}
