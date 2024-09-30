package com.emazon.cart.application.handlers;

import com.emazon.cart.application.dtos.cart.AddArticleToCartRequestDTO;
import com.emazon.cart.application.dtos.cart.ArticlesCartResponseDTO;
import com.emazon.cart.application.dtos.common.ResponseDTO;
import com.emazon.cart.application.dtos.pagination.PaginationRequestDTO;
import com.emazon.cart.application.dtos.searchcriteria.ArticleSearchCriteriaRequestDTO;
import com.emazon.cart.application.dtos.sorting.SortingRequestDTO;

public interface CartHandler {
  ResponseDTO addArticleToCart(AddArticleToCartRequestDTO addArticleToCartRequestDTO);
  ResponseDTO removeArticleFromCart(Long articleId);
  ArticlesCartResponseDTO getCartArticles(SortingRequestDTO sorting, PaginationRequestDTO pagination, ArticleSearchCriteriaRequestDTO searchCriteria);
}
