package net.alterapp.altercar.config;

import net.alterapp.altercar.email.EmailService;
import net.alterapp.altercar.email.EmailServiceFakeImpl;
import net.alterapp.altercar.email.EmailServiceImpl;
import net.alterapp.altercar.repository.AccountRepository;
import net.alterapp.altercar.repository.RoleRepository;
import net.alterapp.altercar.security.service.JwtUserDetailsDevService;
import net.alterapp.altercar.security.service.JwtUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class ServiceBeanConfig {

  private final Environment env;
  private final RoleRepository roleRepository;
  private final AccountRepository accountRepository;

//  @Bean(name = "multipartResolver")
//  public CommonsMultipartResolver multipartResolver() {
//    CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
//    multipartResolver.setMaxUploadSize(100000);
//    return multipartResolver;
//  }

  @Bean
  public JwtUserDetailsService jwtUserDetailsService() {
    return isDev() ? new JwtUserDetailsDevService(roleRepository, accountRepository)
        : new JwtUserDetailsService(accountRepository);
  }

  @Bean
  public EmailService emailService() {
    return isDev() ? new EmailServiceFakeImpl() : new EmailServiceImpl();
  }

  private boolean isDev() {
    return Arrays.asList(env.getActiveProfiles()).contains("dev");
  }

}
