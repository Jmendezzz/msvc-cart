package com.emazon.cart.infrastructure.controllers;

import com.emazon.cart.application.dtos.cart.AddArticleToCartRequestDTO;
import com.emazon.cart.application.dtos.common.ResponseDTO;
import com.emazon.cart.application.handlers.CartHandler;
import com.emazon.cart.infrastructure.adapters.in.controllers.CartController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static com.emazon.cart.domain.utils.constants.cart.CartConstant.ARTICLE_ADDED_SUCCESSFULLY_MESSAGE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CartController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
class CartControllerTest {

  @MockBean
  private CartHandler cartHandler;

  @Autowired
  private MockMvc mockMvc;

  @Test
  @WithMockUser(roles = "CUSTOMER")
  void whenValidRequestShouldAddArticleToCart() throws Exception {
    ResponseDTO responseDTO = new ResponseDTO(ARTICLE_ADDED_SUCCESSFULLY_MESSAGE);

    when(cartHandler.addArticleToCart(any(AddArticleToCartRequestDTO.class))).thenReturn(responseDTO);

    mockMvc.perform(post("/api/v1/cart/add-article")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""
                        {
                            "articleId": 1,
                            "quantity": 5
                        }
                        """))
            .andExpect(status().isOk())
            .andExpect(content().json("""
                        {
                            "message": "The article has been added successfully to your cart"
                        }
                        """));
  }

  @Test
  @WithMockUser(roles = "CUSTOMER")
  void whenArticleIdIsNullShouldReturnBadRequest() throws Exception {
    mockMvc.perform(post("/api/v1/cart/add-article")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""
                        {
                            "articleId": null,
                            "quantity": 5
                        }
                        """))
            .andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser(roles = "CUSTOMER")
  void whenQuantityIsInvalidShouldReturnBadRequest() throws Exception {
    mockMvc.perform(post("/api/v1/cart/add-article")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""
                        {
                            "articleId": 1,
                            "quantity": 0
                        }
                        """))
            .andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser(roles = "CUSTOMER")
  void whenQuantityIsNullShouldReturnBadRequest() throws Exception {
    mockMvc.perform(post("/api/v1/cart/add-article")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""
                        {
                            "articleId": 1,
                            "quantity": null
                        }
                        """))
            .andExpect(status().isBadRequest());
  }
}
