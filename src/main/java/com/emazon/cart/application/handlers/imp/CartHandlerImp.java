package com.emazon.cart.application.handlers.imp;

import com.emazon.cart.application.dtos.cart.AddArticleToCartRequestDTO;
import com.emazon.cart.application.handlers.CartHandler;
import com.emazon.cart.domain.ports.in.usecases.cart.AddArticleToCartUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CartHandlerImp implements CartHandler {
  private final AddArticleToCartUseCase addArticleToCartUseCase;

  @Override
  public void addArticleToCart(AddArticleToCartRequestDTO addArticleToCartRequestDTO) {
    addArticleToCartUseCase.addArticleToCart(
      addArticleToCartRequestDTO.cartId(),
      addArticleToCartRequestDTO.articleId(),
      addArticleToCartRequestDTO.quantity()
    );

  }
}
