package com.emazon.cart.domain.ports.in.usecases.cart;

import com.emazon.cart.domain.models.ArticleSearchCriteria;
import com.emazon.cart.domain.models.ArticlesCart;
import com.emazon.cart.domain.models.Pagination;
import com.emazon.cart.domain.models.Sorting;

public interface CartUseCase {
    void addArticleToCart(Long articleId, Integer quantity);
    void removeArticleFromCart(Long articleId);
    ArticlesCart getArticlesCart(Sorting sorting, Pagination pagination, ArticleSearchCriteria searchCriteria);
}
