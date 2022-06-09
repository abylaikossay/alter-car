package net.alterapp.altercar.email;

public interface EmailService {

  void send(String receiver, String subject, String body);

}
