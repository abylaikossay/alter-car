package net.alterapp.altercar.filter;

import lombok.SneakyThrows;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DisabledCorsFilter extends CorsFilter {

  public DisabledCorsFilter(CorsConfigurationSource configSource) {
    super(configSource);
  }

  @Override
  @SneakyThrows
  protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) {
    response.setHeader("Access-Control-Allow-Origin", "*");
    response.setHeader("Access-Control-Allow-Credentials", "true");
    response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, OPTIONS, POST, PUT");
    response.setHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, "
        + "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, Authorization");
    response.addIntHeader("Access-Control-Max-Age", 10);

    if (CorsUtils.isPreFlightRequest(request)) {
      return;
    }

    filterChain.doFilter(request, response);
  }

}
