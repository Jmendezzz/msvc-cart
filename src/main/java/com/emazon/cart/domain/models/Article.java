package com.emazon.cart.domain.models;

import java.util.List;

public record Article(
    Long id,
    String name,
    String description,
    Double price,
    Integer stock,
    List<Category> categories
) {
}
