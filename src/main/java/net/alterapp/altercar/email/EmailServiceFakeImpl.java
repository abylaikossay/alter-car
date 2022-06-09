package net.alterapp.altercar.email;

import lombok.SneakyThrows;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class EmailServiceFakeImpl implements EmailService {

  @Override
  @SneakyThrows
  public void send(String receiver, String subject, String body) {
    Properties prop = new Properties();
    prop.put("mail.smtp.auth", true);
    prop.put("mail.smtp.starttls.enable", "true");
    prop.put("mail.smtp.host", "localhost");
    prop.put("mail.smtp.port", "5025");

    Session session = Session.getInstance(prop, new Authenticator() {
      @Override
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication("admin", "admin");
      }
    });

    Message message = new MimeMessage(session);
    message.setFrom(new InternetAddress("dev@invest.com"));
    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
    message.setSubject(subject);

    String msg = body;

    MimeBodyPart mimeBodyPart = new MimeBodyPart();
    mimeBodyPart.setContent(msg, "text/html; charset=utf-8");

    Multipart multipart = new MimeMultipart();
    multipart.addBodyPart(mimeBodyPart);

    message.setContent(multipart);

    Transport.send(message);
  }

}
