package com.emazon.cart.infrastructure.config.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.codec.ErrorDecoder;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class FeignConfig {
  private final ObjectMapper objectMapper;
  @Bean
  public JwtTokenInterceptor jwtTokenInterceptor() {
    return new JwtTokenInterceptor();
  }

  @Bean
  public ErrorDecoder feignErrorDecoderConfig() {
    return new FeignErrorDecoderConfig(objectMapper);
  }


}
