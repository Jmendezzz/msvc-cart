package com.emazon.cart.infrastructure.adapters.out.feign.dtos;

public record BrandResponseDTO(
        Long id,
        String name,
        String description
) {
}
