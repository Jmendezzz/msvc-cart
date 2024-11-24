package com.emazon.cart.domain.models;

import java.time.LocalDateTime;
import java.util.List;

public record CartWithArticles(
        Long id,
        Long userId,
        List<CartArticleItem> cartItems,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
