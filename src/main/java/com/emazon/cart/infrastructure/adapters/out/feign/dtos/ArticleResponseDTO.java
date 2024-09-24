package com.emazon.cart.infrastructure.adapters.out.feign.dtos;

import java.util.Set;

public record ArticleResponseDTO(
        Long id,
        String name,
        String description,
        Double price,
        Integer stock,
        BrandResponseDTO brand,
        Set<CategoryResponseDTO> categories
) {
}
