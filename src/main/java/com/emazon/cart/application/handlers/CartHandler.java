package com.emazon.cart.application.handlers;

import com.emazon.cart.application.dtos.cart.AddArticleToCartRequestDTO;

public interface CartHandler {
  void addArticleToCart(AddArticleToCartRequestDTO addArticleToCartRequestDTO);
}
