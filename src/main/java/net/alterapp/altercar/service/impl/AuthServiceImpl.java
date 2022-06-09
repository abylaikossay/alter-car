package net.alterapp.altercar.service.impl;

import net.alterapp.altercar.model.enums.RoleEnum;
import net.alterapp.altercar.model.entity.AccountEntity;
import net.alterapp.altercar.model.entity.AccountSessionEntity;
import net.alterapp.altercar.model.entity.RoleEntity;
import net.alterapp.altercar.repository.AccountRepository;
import net.alterapp.altercar.repository.AccountSessionRepository;
import net.alterapp.altercar.security.JwtRequest;
import net.alterapp.altercar.security.JwtResponse;
import net.alterapp.altercar.security.JwtTokenUtil;
import net.alterapp.altercar.security.UnauthorizedException;
import net.alterapp.altercar.service.AuthService;
import net.alterapp.altercar.service.DateHelper;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final DateHelper dateHelper;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final AccountRepository accountRepository;
    private final AccountSessionRepository sessionRepository;

    @Override
    @Transactional
    public JwtResponse getToken(JwtRequest request) {

        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        final String token = jwtTokenUtil.generateToken(request.getUsername(), authenticate.getAuthorities());
        AccountEntity account = accountRepository.findByUsername(request.getUsername());
        sessionRepository.deleteAllByAccount_Id(account.getId());


        long refreshTokenValidityMinutes = jwtTokenUtil.getJwtRefreshTokenValidityMinutes(authenticate.getAuthorities());
        AccountSessionEntity session = AccountSessionEntity.builder()
                .account(account)
                .refreshToken(UUID.randomUUID().toString())
                .expiryDate(dateHelper.getNow().plus(refreshTokenValidityMinutes, ChronoUnit.MINUTES))
                .build();

        sessionRepository.save(session);
        return JwtResponse.builder()
                .token(token)
                .refreshToken(session.getRefreshToken())
                .role(account.getRole().getId())
                .build();
    }

    @Override
    public JwtResponse refreshToken(String refreshToken) {
        if (Strings.isBlank(refreshToken)) {
            throw new UnauthorizedException(new InsufficientAuthenticationException("Null or empty refresh token!"));
        }

        AccountSessionEntity oldSession = sessionRepository.findByRefreshToken(refreshToken);
        if (oldSession == null) {
            throw new UnauthorizedException(new InsufficientAuthenticationException("Session not found!"));
        }
        if (oldSession.getExpiryDate().isBefore(dateHelper.getNow())) {
            sessionRepository.delete(oldSession);
            throw new UnauthorizedException(new InsufficientAuthenticationException("Session expired!"));
        }
        String username = oldSession.getAccount().getUsername();

        AccountEntity account = accountRepository.findByUsername(username);

        long refreshTokenValidityMinutes = jwtTokenUtil.getJwtRefreshTokenValidityMinutes(account.getRole());
        AccountSessionEntity newSession = AccountSessionEntity.builder()
                .account(account)
                .refreshToken(UUID.randomUUID().toString())
                .expiryDate(dateHelper.getNow().plus(refreshTokenValidityMinutes, ChronoUnit.MINUTES))
                .build();

        sessionRepository.delete(oldSession);
        sessionRepository.save(newSession);

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (account.getRole() != null) {
            authorities.add(new SimpleGrantedAuthority(String.valueOf(account.getRole().getId())));
        }

        return JwtResponse.builder()
                .token(jwtTokenUtil.generateToken(account.getUsername(), authorities))
                .refreshToken(newSession.getRefreshToken())
                .build();
    }

}
