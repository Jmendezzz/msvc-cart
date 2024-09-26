package com.emazon.cart.application.handlers;

import com.emazon.cart.application.dtos.cart.AddArticleToCartRequestDTO;
import com.emazon.cart.application.dtos.common.ResponseDTO;

public interface CartHandler {
  ResponseDTO addArticleToCart(AddArticleToCartRequestDTO addArticleToCartRequestDTO);
  ResponseDTO removeArticleFromCart(Long articleId);
}
