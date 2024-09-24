package com.emazon.cart.infrastructure.config.feign;


import com.emazon.cart.domain.exceptions.BusinessException;
import com.emazon.cart.infrastructure.exceptions.ExceptionResponse;
import com.emazon.cart.infrastructure.exceptions.feign.InternalFeignException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.IOException;

@AllArgsConstructor
public class FeignErrorDecoderConfig implements ErrorDecoder {
  private final ObjectMapper objectMapper;
  @Override
  public Exception decode(String methodKey, Response response) {
    if(response.status() < HttpStatus.INTERNAL_SERVER_ERROR.value() && response.status() >= HttpStatus.BAD_REQUEST.value()){
      try {
        ExceptionResponse exceptionResponse = objectMapper.readValue(response.body().asInputStream(), ExceptionResponse.class);
        return new BusinessException(exceptionResponse.getMessage(), exceptionResponse.getCode());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
      return new InternalFeignException();
  }
}
