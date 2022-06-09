package net.alterapp.altercar.filter;

import lombok.Builder;
import lombok.Getter;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class RequestContextFilter implements HandlerInterceptor {

  public static final ThreadLocal<RequestContext> CONTEXT = new ThreadLocal<>();

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

    RequestContext context = RequestContext.builder()
        .requestId(UUID.randomUUID().toString())
        .build();

    ThreadContext.put("requestId", context.getRequestId());

    CONTEXT.set(context);
    return true;
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    CONTEXT.remove();
  }

  @Getter
  @Builder
  public static class RequestContext {

    private final String requestId;

  }

}
