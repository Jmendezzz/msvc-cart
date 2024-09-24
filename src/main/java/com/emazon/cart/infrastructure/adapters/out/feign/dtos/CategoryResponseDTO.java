package com.emazon.cart.infrastructure.adapters.out.feign.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CategoryResponseDTO(
        Long id,
        String name) {
}
