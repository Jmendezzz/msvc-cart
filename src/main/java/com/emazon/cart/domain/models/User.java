package com.emazon.cart.domain.models;

public record User(
        Long id,
        String email,
        String role
) {
}
