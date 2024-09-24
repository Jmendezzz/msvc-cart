package com.emazon.cart.infrastructure.adapters.out.persistence.mappers;

import com.emazon.cart.domain.models.Cart;
import com.emazon.cart.infrastructure.adapters.out.persistence.entities.CartEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartEntityMapper {
  Cart toDomain(CartEntity cartEntity);
  CartEntity toEntity(Cart cart);
}
