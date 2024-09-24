package com.emazon.cart.application.handlers;

import com.emazon.cart.application.dtos.cart.AddArticleToCartRequestDTO;
import com.emazon.cart.application.handlers.imp.CartHandlerImp;
import com.emazon.cart.domain.ports.in.usecases.cart.AddArticleToCartUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartHandlerTest {
  @Mock
  private AddArticleToCartUseCase addArticleToCartUseCase;
  @InjectMocks
  private CartHandlerImp cartHandler;


  @Test
  void whenAddArticleToCartIsCalled_ShouldInvokeAddArticleToCartUseCase() {
    AddArticleToCartRequestDTO requestDTO = new AddArticleToCartRequestDTO(2L, 3);

    cartHandler.addArticleToCart(requestDTO);

    verify(addArticleToCartUseCase, times(1)).addArticleToCart(2L, 3);
  }
}
