package com.emazon.cart.application.handlers.imp;

import com.emazon.cart.application.dtos.cart.AddArticleToCartRequestDTO;
import com.emazon.cart.application.dtos.common.ResponseDTO;
import com.emazon.cart.application.handlers.CartHandler;
import com.emazon.cart.domain.ports.in.usecases.cart.AddArticleToCartUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.emazon.cart.domain.utils.constants.cart.CartConstant.ARTICLE_ADDED_SUCCESSFULLY_MESSAGE;

@Service
@AllArgsConstructor
public class CartHandlerImp implements CartHandler {
  private final AddArticleToCartUseCase addArticleToCartUseCase;

  @Override
  public ResponseDTO addArticleToCart(AddArticleToCartRequestDTO addArticleToCartRequestDTO) {
    addArticleToCartUseCase.addArticleToCart(
            addArticleToCartRequestDTO.articleId(),
            addArticleToCartRequestDTO.quantity()
    );

    return new ResponseDTO(ARTICLE_ADDED_SUCCESSFULLY_MESSAGE);
  }
}
