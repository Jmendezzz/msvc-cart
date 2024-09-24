package com.emazon.cart.infrastructure.config.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class JwtTokenInterceptor implements RequestInterceptor{

  @Override
  public void apply(RequestTemplate template) {
    ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    if (requestAttributes != null) {
      HttpServletRequest request = requestAttributes.getRequest();
      String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
      if (StringUtils.isNotBlank(authorization)) {
        template.header(HttpHeaders.AUTHORIZATION, authorization);
      }
    }
  }
}
