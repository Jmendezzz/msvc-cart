package com.emazon.cart.application.handlers;

import com.emazon.cart.application.dtos.cart.AddArticleToCartRequestDTO;
import com.emazon.cart.application.handlers.imp.CartHandlerImp;
import com.emazon.cart.domain.ports.in.usecases.cart.CartUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartHandlerTest {
  @Mock
  private CartUseCase cartUseCase;
  @InjectMocks
  private CartHandlerImp cartHandler;


  @Test
  void whenAddArticleToCartIsCalled_ShouldInvokeAddArticleToCartUseCase() {
    AddArticleToCartRequestDTO requestDTO = new AddArticleToCartRequestDTO(2L, 3);

    cartHandler.addArticleToCart(requestDTO);

    verify(cartUseCase, times(1)).addArticleToCart(2L, 3);
  }
  @Test
  void whenRemoveArticleFromCartIsCalled_ShouldInvokeRemoveArticleFromCartUseCase() {
    cartHandler.removeArticleFromCart(2L);

    verify(cartUseCase, times(1)).removeArticleFromCart(2L);
  }
}
