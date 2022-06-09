package net.alterapp.altercar.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.alterapp.altercar.model.enums.RoleEnum;
import net.alterapp.altercar.model.entity.RoleEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.internal.Function;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class JwtTokenUtil {

  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.token_validity_seconds:18000}")
  public long jwtTokenValiditySeconds;

  @Value("${jwt.token_refresh_validity_minutes:43200}")
  public long jwtRefreshTokenValidityMinutes;

  private static final Long ADMIN_ACCESS_TOKEN_VALIDITY_SECONDS = 600L;
  private static final Long ADMIN_REFRESH_TOKEN_VALIDITY_MINUTES = 60L;

  public String getUsernameFromToken(String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }

  public Date getExpirationDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

  private Claims getAllClaimsFromToken(String token) {
    return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
  }

  private Boolean isTokenExpired(String token) {
    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(new Date());
  }

  public String generateToken(String username, Collection<? extends GrantedAuthority> authorities) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("roles", getRolesClaim(authorities));

    return Jwts.builder()
        .setClaims(claims)
        .setSubject(username)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + getTokenValiditySeconds(authorities) * 1000))
        .signWith(SignatureAlgorithm.HS512, secret)
        .compact();
  }

  public long getTokenValiditySeconds(Collection<? extends GrantedAuthority> authorities) {
    if (authorities != null
        && authorities.stream().anyMatch(a -> String.valueOf(RoleEnum.ROLE_ADMIN).equals(a.getAuthority()))) {
      return ADMIN_ACCESS_TOKEN_VALIDITY_SECONDS;
    }
    return jwtTokenValiditySeconds;
  }

  public long getJwtRefreshTokenValidityMinutes(Collection<? extends GrantedAuthority> authorities) {
    if (authorities != null
        && authorities.stream().anyMatch(a -> String.valueOf(RoleEnum.ROLE_ADMIN).equals(a.getAuthority()))) {
      return ADMIN_REFRESH_TOKEN_VALIDITY_MINUTES;
    }
    return jwtRefreshTokenValidityMinutes;
  }

  public long getJwtRefreshTokenValidityMinutes(RoleEntity role) {
    if (role != null && role.getId() == RoleEnum.ROLE_ADMIN) {
      return ADMIN_REFRESH_TOKEN_VALIDITY_MINUTES;
    }
    return jwtRefreshTokenValidityMinutes;
  }

  private String getRolesClaim(Collection<? extends GrantedAuthority> authorities) {
    if (authorities == null) {
      return null;
    }
    StringBuilder sb = new StringBuilder();

    authorities.forEach(a -> sb.append(" ").append(a.getAuthority()));

    return sb.toString().trim();
  }

  public Boolean validateToken(String token, UserDetails userDetails) {
    final String username = getUsernameFromToken(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

}
