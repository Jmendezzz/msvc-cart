package com.emazon.cart.application.dtos.cart;

import com.emazon.cart.domain.models.CartArticleItem;
import com.emazon.cart.domain.models.Paginated;

public record ArticlesCartResponseDTO(
        Paginated<CartArticleItem> paginatedArticles,
        Double totalPrice
) {

}
