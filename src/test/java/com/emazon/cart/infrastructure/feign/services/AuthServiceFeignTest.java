package com.emazon.cart.infrastructure.feign.services;

import com.emazon.cart.domain.models.User;
import com.emazon.cart.infrastructure.adapters.in.security.models.CustomUserDetails;
import com.emazon.cart.infrastructure.adapters.out.feign.clients.AuthFeignClient;
import com.emazon.cart.infrastructure.adapters.out.services.AuthServiceFeignAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceFeignTest {

  @Mock
  private AuthFeignClient authFeign;

  @InjectMocks
  private AuthServiceFeignAdapter authServiceFeignAdapter;

  @BeforeEach
  void setUp() {
    User user = new User(
            1L,
            "email",
            "role"
    );
    SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(new CustomUserDetails(user), null));
  }

  @Test
  void getUserId() {
    Long expectedUserId = 1L;
    Long actualUserId = authServiceFeignAdapter.getUserId();
    assertEquals(expectedUserId, actualUserId);
  }

  @Test
  void getUserByToken() {
    String token = "token";
    User expectedUser = new User(
            1L,
            "email",
            "role"
    );
    when(authFeign.getUserDetails(token)).thenReturn(expectedUser);

    Optional<User> actualUser = authServiceFeignAdapter.getUserByToken(token);
    assertEquals(Optional.of(expectedUser), actualUser);
  }

}
