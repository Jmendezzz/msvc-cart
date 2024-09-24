package com.emazon.cart.domain.ports.in.usecases.cart;

public interface AddArticleToCartUseCase {
    void addArticleToCart(Long articleId, Integer quantity);
}
