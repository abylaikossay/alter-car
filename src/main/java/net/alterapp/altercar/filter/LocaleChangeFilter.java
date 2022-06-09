//package net.alterapp.tram.filter;
//
//import net.alterapp.tram.model.LangEnum;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.LocaleResolver;
//import org.springframework.web.servlet.support.RequestContextUtils;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.Locale;
//
//public class LocaleChangeFilter implements HandlerInterceptor {
//
//  @Override
//  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
//
//    LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
//
//    if (localeResolver == null) {
//      throw new IllegalStateException("Locale resolver not found!");
//    }
//
//    LangEnum lang = LangEnum.parseOrNull(request.getHeader("Accept-Language"));
//    localeResolver.setLocale(request, response, getLocale(lang));
//
//    return true;
//  }
//
//  private Locale getLocale(LangEnum lang) {
//    if (lang == LangEnum.EN) {
//      return Locale.ENGLISH;
//    }
//
//    return new Locale("ru", "RU");
//  }
//
//
//  @Override
//  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
//  }
//
//}
