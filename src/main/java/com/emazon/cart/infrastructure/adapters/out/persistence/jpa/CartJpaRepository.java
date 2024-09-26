package com.emazon.cart.infrastructure.adapters.out.persistence.jpa;

import com.emazon.cart.infrastructure.adapters.out.persistence.entities.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartJpaRepository extends JpaRepository<CartEntity, Long> {
  Optional<CartEntity> findByUserId(Long userId);
}
