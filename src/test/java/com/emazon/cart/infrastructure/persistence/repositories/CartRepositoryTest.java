package com.emazon.cart.infrastructure.persistence.repositories;

import com.emazon.cart.domain.models.Cart;
import com.emazon.cart.infrastructure.adapters.out.persistence.entities.CartEntity;
import com.emazon.cart.infrastructure.adapters.out.persistence.jpa.CartJpaRepository;
import com.emazon.cart.infrastructure.adapters.out.persistence.mappers.CartEntityMapper;
import com.emazon.cart.infrastructure.adapters.out.persistence.repositories.CartRepositoryMysqlAdapter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartRepositoryTest {
  @Mock
  private CartJpaRepository cartJpaRepository;

  @Mock
  private CartEntityMapper cartEntityMapper;

  @InjectMocks
  private CartRepositoryMysqlAdapter cartRepositoryMysqlAdapter;


  @Test
  void whenSaveCartShouldReturnSavedCart() {
    Cart cart = new Cart();
    CartEntity cartEntity = new CartEntity();
    CartEntity savedCartEntity = new CartEntity();
    Cart savedCart = new Cart();

    when(cartEntityMapper.toEntity(cart)).thenReturn(cartEntity);
    when(cartJpaRepository.save(cartEntity)).thenReturn(savedCartEntity);
    when(cartEntityMapper.toDomain(savedCartEntity)).thenReturn(savedCart);

    Cart result = cartRepositoryMysqlAdapter.save(cart);

    assertNotNull(result);
    assertEquals(savedCart, result);
    verify(cartEntityMapper).toEntity(cart);
    verify(cartJpaRepository).save(cartEntity);
    verify(cartEntityMapper).toDomain(savedCartEntity);
  }

  @Test
  void whenFindByIdShouldReturnCartIfExists() {
    Long id = 1L;
    CartEntity cartEntity = new CartEntity();
    Cart cart = new Cart();

    when(cartJpaRepository.findById(id)).thenReturn(Optional.of(cartEntity));
    when(cartEntityMapper.toDomain(cartEntity)).thenReturn(cart);

    Optional<Cart> result = cartRepositoryMysqlAdapter.findById(id);

    assertTrue(result.isPresent());
    assertEquals(cart, result.get());
    verify(cartJpaRepository).findById(id);
    verify(cartEntityMapper).toDomain(cartEntity);
  }

  @Test
  void whenFindByIdShouldReturnEmptyIfNotFound() {
    Long id = 1L;

    when(cartJpaRepository.findById(id)).thenReturn(Optional.empty());

    Optional<Cart> result = cartRepositoryMysqlAdapter.findById(id);

    assertTrue(result.isEmpty());
    verify(cartJpaRepository).findById(id);
    verify(cartEntityMapper, never()).toDomain(any());
  }

  @Test
  void whenFindByUserIdShouldReturnCartIfExists() {
    Long userId = 123L;
    CartEntity cartEntity = new CartEntity();
    Cart cart = new Cart();

    when(cartJpaRepository.findByUserId(userId)).thenReturn(Optional.of(cartEntity));
    when(cartEntityMapper.toDomain(cartEntity)).thenReturn(cart);

    Optional<Cart> result = cartRepositoryMysqlAdapter.findByUserId(userId);

    assertTrue(result.isPresent());
    assertEquals(cart, result.get());
    verify(cartJpaRepository).findByUserId(userId);
    verify(cartEntityMapper).toDomain(cartEntity);
  }

  @Test
  void whenFindByUserIdShouldReturnEmptyIfNotFound() {
    Long userId = 123L;

    when(cartJpaRepository.findByUserId(userId)).thenReturn(Optional.empty());

    Optional<Cart> result = cartRepositoryMysqlAdapter.findByUserId(userId);

    assertTrue(result.isEmpty());
    verify(cartJpaRepository).findByUserId(userId);
    verify(cartEntityMapper, never()).toDomain(any());
  }
}
