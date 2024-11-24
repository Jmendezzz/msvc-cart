package com.emazon.cart.application.handlers.imp;

import com.emazon.cart.application.dtos.cart.AddArticleToCartRequestDTO;
import com.emazon.cart.application.dtos.cart.ArticlesCartResponseDTO;
import com.emazon.cart.application.dtos.cart.CartResponseDTO;
import com.emazon.cart.application.dtos.common.ResponseDTO;
import com.emazon.cart.application.dtos.pagination.PaginationRequestDTO;
import com.emazon.cart.application.dtos.searchcriteria.ArticleSearchCriteriaRequestDTO;
import com.emazon.cart.application.dtos.sorting.SortingRequestDTO;
import com.emazon.cart.application.handlers.CartHandler;
import com.emazon.cart.application.mappers.CartMapperDTO;
import com.emazon.cart.application.mappers.PaginationMapperDTO;
import com.emazon.cart.application.mappers.SearchCriteriaMapperDTO;
import com.emazon.cart.application.mappers.SortingMapperDTO;
import com.emazon.cart.domain.models.ArticlesCart;
import com.emazon.cart.domain.models.CartWithArticles;
import com.emazon.cart.domain.ports.in.usecases.cart.CartUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.emazon.cart.domain.utils.constants.cart.CartConstant.ARTICLE_ADDED_SUCCESSFULLY_MESSAGE;
import static com.emazon.cart.domain.utils.constants.cart.CartConstant.ARTICLE_REMOVED_SUCCESSFULLY_MESSAGE;

@Service
@AllArgsConstructor
public class CartHandlerImp implements CartHandler {
  private final CartUseCase cartUseCase;
  private final CartMapperDTO cartMapper;
  private final PaginationMapperDTO paginationMapper;
  private final SortingMapperDTO sortingMapper;
  private final SearchCriteriaMapperDTO searchCriteriaMapper;

  @Override
  public ResponseDTO addArticleToCart(AddArticleToCartRequestDTO addArticleToCartRequestDTO) {
    cartUseCase.addArticleToCart(
            addArticleToCartRequestDTO.articleId(),
            addArticleToCartRequestDTO.quantity()
    );

    return new ResponseDTO(ARTICLE_ADDED_SUCCESSFULLY_MESSAGE);
  }

  @Override
  public ResponseDTO removeArticleFromCart(Long articleId) {
    cartUseCase.removeArticleFromCart(articleId);
    return new ResponseDTO(ARTICLE_REMOVED_SUCCESSFULLY_MESSAGE);
  }

  @Override
  public ArticlesCartResponseDTO getCartArticles(SortingRequestDTO sorting, PaginationRequestDTO pagination, ArticleSearchCriteriaRequestDTO searchCriteria) {
    ArticlesCart  articlesCart = cartUseCase.getArticlesCart(
            sortingMapper.toDomain(sorting),
            paginationMapper.toDomain(pagination),
            searchCriteriaMapper.toDomain(searchCriteria)
    );
    return cartMapper.toDTO(articlesCart);
  }

  @Override
  public CartWithArticles getUserCart() {
    return cartUseCase.getUserCart();
  }
}
