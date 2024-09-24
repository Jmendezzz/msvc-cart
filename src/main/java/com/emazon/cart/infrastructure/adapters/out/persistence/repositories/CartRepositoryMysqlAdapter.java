package com.emazon.cart.infrastructure.adapters.out.persistence.repositories;

import com.emazon.cart.domain.models.Cart;
import com.emazon.cart.domain.ports.out.repositories.CartRepository;
import com.emazon.cart.infrastructure.adapters.out.persistence.entities.CartEntity;
import com.emazon.cart.infrastructure.adapters.out.persistence.jpa.CartJpaRepository;
import com.emazon.cart.infrastructure.adapters.out.persistence.mappers.CartEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class CartRepositoryMysqlAdapter implements CartRepository {
  private final CartJpaRepository cartRepository;
  private final CartEntityMapper mapper;
  @Override
  public Cart save(Cart cart) {
    CartEntity cartEntityToSave = mapper.toEntity(cart);
    return mapper.toDomain(cartRepository.save(cartEntityToSave));
  }

  @Override
  public Optional<Cart> findById(Long id) {
    return cartRepository.findById(id).map(mapper::toDomain);
  }

  @Override
  public Optional<Cart> findByUserId(Long userId) {
    return cartRepository.findByUserId(userId).map(mapper::toDomain);
  }
}
