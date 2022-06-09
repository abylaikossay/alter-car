package net.alterapp.altercar.filter;

import io.jsonwebtoken.ExpiredJwtException;
import net.alterapp.altercar.security.JwtTokenUtil;
import net.alterapp.altercar.security.service.JwtUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

  private final JwtTokenUtil jwtTokenUtil;
  private final JwtUserDetailsService jwtUserDetailsService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {

    final String requestTokenHeader = request.getHeader("Authorization");

    String username = null;
    String jwtToken = null;

    if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
      jwtToken = requestTokenHeader.substring(7);
      username = getUserName(jwtToken);
    }

    // Не указан пользователь или аутентификация уже есть
    if (username == null || SecurityContextHolder.getContext().getAuthentication() != null) {
      chain.doFilter(request, response);
      return;
    }

    UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);

    if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
      configureContext(request, userDetails);
    }

    chain.doFilter(request, response);
  }

  private String getUserName(String jwtToken) {
    try {
      return jwtTokenUtil.getUsernameFromToken(jwtToken);
    } catch (IllegalArgumentException e) {
      log.error("Unable to get JWT Token");
    } catch (ExpiredJwtException e) {
      log.error("JWT Token has expired");
    }
    return null;
  }

  private void configureContext(HttpServletRequest request, UserDetails userDetails) {
    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
        userDetails, null, userDetails.getAuthorities());
    usernamePasswordAuthenticationToken
        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    // After setting the Authentication in the context, we specify
    // that the current user is authenticated. So it passes the
    // Spring Security Configurations successfully.
    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
  }

}
