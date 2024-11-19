package com.emazon.cart.domain.models;

public record CartArticleItem(
        Article article,
        Integer quantity
) {
}
