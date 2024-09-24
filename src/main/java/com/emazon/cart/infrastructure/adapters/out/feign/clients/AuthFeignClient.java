package com.emazon.cart.infrastructure.adapters.out.feign.clients;

import com.emazon.cart.domain.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import static com.emazon.cart.infrastructure.utils.constants.FeignConstant.USER_CLIENT;
import static com.emazon.cart.infrastructure.utils.constants.FeignConstant.USER_URL;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@FeignClient(name = USER_CLIENT, url = USER_URL)
public interface AuthFeignClient {
  @GetMapping("/api/v1/auth/validate-token")
  User getUserDetails(@RequestHeader(AUTHORIZATION) String token);

}
