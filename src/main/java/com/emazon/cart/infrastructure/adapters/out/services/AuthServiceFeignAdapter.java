package com.emazon.cart.infrastructure.adapters.out.services;

import com.emazon.cart.domain.models.User;
import com.emazon.cart.domain.ports.out.services.AuthService;
import com.emazon.cart.infrastructure.adapters.in.security.models.CustomUserDetails;
import com.emazon.cart.infrastructure.adapters.out.feign.clients.AuthFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthServiceFeignAdapter implements AuthService {
  private final AuthFeignClient authFeign;

  @Override
  public Long getUserId() {
    CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    return customUserDetails.getId();
  }

  @Override
  public Optional<User> getUserByToken(String token) {
    return Optional.ofNullable(authFeign.getUserDetails(token));
  }
}
