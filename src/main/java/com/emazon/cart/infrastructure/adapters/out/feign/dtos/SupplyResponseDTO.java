package com.emazon.cart.infrastructure.adapters.out.feign.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SupplyResponseDTO(
        Long id,
        Long articleId,
        Integer quantity,
        Long createdBy,
        LocalDateTime createdAt,
        LocalDateTime availableAt
) {
}
