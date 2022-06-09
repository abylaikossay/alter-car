package net.alterapp.altercar.service;

import net.alterapp.altercar.security.JwtRequest;
import net.alterapp.altercar.security.JwtResponse;

public interface AuthService {

  JwtResponse getToken(JwtRequest request);

  JwtResponse refreshToken(String refreshToken);

}
