package com.emazon.cart.application.mappers;

import com.emazon.cart.application.dtos.cart.ArticlesCartResponseDTO;
import com.emazon.cart.domain.models.ArticlesCart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapperDTO {
  ArticlesCartResponseDTO toDTO(ArticlesCart articlesCart);
}
