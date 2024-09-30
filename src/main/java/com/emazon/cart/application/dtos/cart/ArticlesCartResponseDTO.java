package com.emazon.cart.application.dtos.cart;

import com.emazon.cart.domain.models.Article;
import com.emazon.cart.domain.models.Paginated;

public record ArticlesCartResponseDTO(
        Paginated<Article> paginatedArticles,
        Double totalPrice
) {

}
