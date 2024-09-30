package com.emazon.cart.application.dtos.searchcriteria;

public record ArticleSearchCriteriaRequestDTO(
        String articleName,
        Long categoryId,
        Long brandId
) {
}
