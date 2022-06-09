package net.alterapp.altercar.events;

import org.springframework.context.ApplicationEvent;

public class UpdateAccountPasswordEvent extends ApplicationEvent {

  public UpdateAccountPasswordEvent(Object source) {
    super(source);
  }

}
