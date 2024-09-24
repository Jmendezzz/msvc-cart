package com.emazon.cart.domain.ports.out.repositories;

import com.emazon.cart.domain.models.Cart;

import java.util.Optional;

public interface CartRepository {
  Cart save(Cart cart);
  Optional<Cart> findById(Long id);
  Optional<Cart> findByUserId(Long userId);
}
