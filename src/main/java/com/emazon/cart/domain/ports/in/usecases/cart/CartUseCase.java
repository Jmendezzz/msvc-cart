package com.emazon.cart.domain.ports.in.usecases.cart;

public interface CartUseCase {
    void addArticleToCart(Long articleId, Integer quantity);
    void removeArticleFromCart(Long articleId);
}
