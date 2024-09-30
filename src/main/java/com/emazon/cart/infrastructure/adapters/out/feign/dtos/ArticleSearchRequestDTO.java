package com.emazon.cart.infrastructure.adapters.out.feign.dtos;

import com.emazon.cart.domain.models.ArticleSearchCriteria;
import com.emazon.cart.domain.models.Pagination;
import com.emazon.cart.domain.models.Sorting;

public record ArticleSearchRequestDTO(
        Pagination pagination,
        Sorting sorting,
        ArticleSearchCriteria searchCriteria

) {
}
