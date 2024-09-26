package com.emazon.cart.domain.ports.out.services;

import com.emazon.cart.domain.models.User;

import java.util.Optional;

public interface AuthService {
  Long getUserId();
  Optional<User> getUserByToken(String token);
}
