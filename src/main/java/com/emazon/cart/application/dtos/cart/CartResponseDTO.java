package com.emazon.cart.application.dtos.cart;

import com.emazon.cart.domain.models.CartArticleItem;

import java.time.LocalDateTime;
import java.util.List;

public record CartResponseDTO(
        Long id,
        Long userId,
        List<CartArticleItem> cartItems,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
