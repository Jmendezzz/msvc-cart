package com.emazon.cart.infrastructure.persistence.mappes;

import com.emazon.cart.domain.models.Cart;
import com.emazon.cart.domain.models.CartItem;
import com.emazon.cart.infrastructure.adapters.out.persistence.entities.CartEntity;
import com.emazon.cart.infrastructure.adapters.out.persistence.entities.CartItemEntity;
import com.emazon.cart.infrastructure.adapters.out.persistence.mappers.CartEntityMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CartEntityMapperTest {

  private CartEntityMapperImpl cartEntityMapper;

  @BeforeEach
  void setUp() {
    cartEntityMapper = new CartEntityMapperImpl();
  }

  @Test
  void whenCartEntityIsNullShouldReturnNullCartDomain() {
    Cart cart = cartEntityMapper.toDomain(null);
    assertNull(cart);
  }

  @Test
  void whenCartDomainIsNullShouldReturnNullCartEntity() {
    CartEntity cartEntity = cartEntityMapper.toEntity(null);
    assertNull(cartEntity);
  }

  @Test
  void whenValidCartEntityShouldMapToDomain() {
    CartItemEntity cartItemEntity = new CartItemEntity();
    cartItemEntity.setId(1L);
    cartItemEntity.setArticleId(2L);
    cartItemEntity.setQuantity(3);

    CartEntity cartEntity = new CartEntity();
    cartEntity.setId(1L);
    cartEntity.setUserId(123L);
    cartEntity.setCartItems(List.of(cartItemEntity));
    cartEntity.setCreatedAt(LocalDateTime.now());
    cartEntity.setUpdatedAt(LocalDateTime.now());

    Cart cart = cartEntityMapper.toDomain(cartEntity);

    assertNotNull(cart);
    assertEquals(1L, cart.getId());
    assertEquals(123L, cart.getUserId());
    assertEquals(1, cart.getCartItems().size());
    assertEquals(2L, cart.getCartItems().get(0).getArticleId());
    assertEquals(3, cart.getCartItems().get(0).getQuantity());
  }

  @Test
  void whenValidCartShouldMapToEntity() {
    CartItem cartItem = new CartItem();
    cartItem.setId(1L);
    cartItem.setArticleId(2L);
    cartItem.setQuantity(3);

    Cart cart = new Cart();
    cart.setId(1L);
    cart.setUserId(123L);
    cart.setCartItems(List.of(cartItem));
    cart.setCreatedAt(LocalDateTime.now());
    cart.setUpdatedAt(LocalDateTime.now());

    CartEntity cartEntity = cartEntityMapper.toEntity(cart);

    assertNotNull(cartEntity);
    assertEquals(1L, cartEntity.getId());
    assertEquals(123L, cartEntity.getUserId());
    assertEquals(1, cartEntity.getCartItems().size());
    assertEquals(2L, cartEntity.getCartItems().get(0).getArticleId());
    assertEquals(3, cartEntity.getCartItems().get(0).getQuantity());
  }

}
