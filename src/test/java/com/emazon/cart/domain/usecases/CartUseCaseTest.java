package com.emazon.cart.domain.usecases;

import com.emazon.cart.domain.exceptions.cart.CartItemNotFoundException;
import com.emazon.cart.domain.exceptions.cart.CategoryLimitExceededException;
import com.emazon.cart.domain.exceptions.cart.NotEnoughStockException;
import com.emazon.cart.domain.models.Article;
import com.emazon.cart.domain.models.Cart;
import com.emazon.cart.domain.models.CartItem;
import com.emazon.cart.domain.models.Category;
import com.emazon.cart.domain.ports.out.repositories.CartRepository;
import com.emazon.cart.domain.ports.out.services.AuthService;
import com.emazon.cart.domain.ports.out.services.StockService;
import com.emazon.cart.domain.ports.out.services.SupplyService;
import com.emazon.cart.domain.usecases.cart.CartUseCaseImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartUseCaseTest {
  @Mock
  private CartRepository cartRepository;

  @Mock
  private AuthService authService;

  @Mock
  private StockService stockService;

  @Mock
  private SupplyService supplyService;

  @InjectMocks
  private CartUseCaseImp cartUseCaseImp;

  private Cart cart;
  private Article article;
  private final Long articleId = 2L;
  private final Integer quantity = 3;

  @BeforeEach
  void setUp() {
    cart = new Cart();
    cart.setUserId(1L);
    cart.setCartItems(new ArrayList<>());
    cart.setCreatedAt(LocalDateTime.now());
    cart.setUpdatedAt(LocalDateTime.now());

    article = new Article(
            articleId,
            "Test Article",
            "Test Description",
            10.0,
            10,
            List.of(new Category(1L, "Category")),
            null
    );

    when(authService.getUserId()).thenReturn(1L);
  }

  @Test
  void whenAddArticleToCartWithValidDataShouldUpdateCart() {
    when(cartRepository.findByUserId(1L)).thenReturn(Optional.of(cart));
    when(stockService.getArticleById(articleId)).thenReturn(article);

    cartUseCaseImp.addArticleToCart(articleId, quantity);

    verify(cartRepository).save(cart);
  }

  @Test
  void whenCartDoesNotExist_ShouldCreateNewCartAndAddArticle() {
    when(cartRepository.findByUserId(1L)).thenReturn(Optional.empty());
    when(stockService.getArticleById(articleId)).thenReturn(article);
    when(cartRepository.save(any(Cart.class))).thenReturn(cart);

    cartUseCaseImp.addArticleToCart(articleId, quantity);

    verify(cartRepository, times(2)).save(any(Cart.class));
  }

  @Test
  void whenArticleStockIsZero_ShouldThrowNotEnoughStockException() {
    article = new Article(
            articleId,
            "Article",
            "Description",
            10.0,
            0,
            List.of(new Category(1L, "Category")),
            null
    );
    when(cartRepository.findByUserId(1L)).thenReturn(Optional.of(cart));
    when(stockService.getArticleById(articleId)).thenReturn(article);
    when(supplyService.getNextArticleSupplyDate(articleId)).thenReturn(Optional.of(LocalDateTime.now().plusDays(10)));

    assertThrows(NotEnoughStockException.class, () -> cartUseCaseImp.addArticleToCart(articleId, quantity));
  }

  @Test
  void whenArticleStockIsLessThanQuantity_ShouldThrowNotEnoughStockException() {
    article = new Article(
            articleId,
            "Article",
            "Description",
            10.0,
            2,
            List.of(new Category(1L, "Category")),
            null
    );
    when(cartRepository.findByUserId(1L)).thenReturn(Optional.of(cart));
    when(stockService.getArticleById(articleId)).thenReturn(article);
    when(supplyService.getNextArticleSupplyDate(articleId)).thenReturn(Optional.of(LocalDateTime.now().plusDays(10)));

    assertThrows(NotEnoughStockException.class, () -> cartUseCaseImp.addArticleToCart(articleId, quantity));
  }

  @Test
  void whenCategoryLimitIsExceeded_ShouldThrowCategoryLimitExceededException() {
    List<Article> cartArticles = List.of(
            new Article(3L, "Article 1", "Description", 10.0, 2, List.of(new Category(1L, "Category")),null),
            new Article(4L, "Article 2", "Description", 10.0, 2, List.of(new Category(1L, "Category")), null),
            new Article(5L, "Article 3", "Description", 10.0, 2, List.of(new Category(1L, "Category")), null)
    );

    when(cartRepository.findByUserId(1L)).thenReturn(Optional.of(cart));
    when(stockService.getArticleById(articleId)).thenReturn(article);
    when(stockService.getArticlesByIds(any())).thenReturn(cartArticles);

    assertThrows(CategoryLimitExceededException.class, () -> cartUseCaseImp.addArticleToCart(articleId, quantity));
  }

  @Test
  void whenRemoveArticleFromCartAndExists_ShouldUpdateCart() {
    Long articleId = 10L;
    CartItem cartItem = new CartItem(1L, articleId, 1);
    cart.setCartItems(new ArrayList<>(List.of(cartItem)));

    when(authService.getUserId()).thenReturn(1L);
    when(cartRepository.findByUserId(1L)).thenReturn(Optional.of(cart));

    cartUseCaseImp.removeArticleFromCart(articleId);

    assertTrue(cart.getCartItems().isEmpty());
    verify(cartRepository).save(cart);
  }

  @Test
  void whenRemoveArticleFromCartAndNotExists_ShouldThrowCartItemNotFoundException() {
    Long articleId = 10L;

    when(authService.getUserId()).thenReturn(1L);
    when(cartRepository.findByUserId(1L)).thenReturn(Optional.of(cart));

    assertThrows(CartItemNotFoundException.class, () -> cartUseCaseImp.removeArticleFromCart(articleId));

    verify(cartRepository, never()).save(any(Cart.class));
  }

  @Test
  void whenCategoryLimitIsNotExceeded_ShouldAddArticleToCart() {
    List<Article> cartArticles = List.of(
            new Article(3L, "Article 1", "Description", 10.0, 2, List.of(new Category(1L, "Category")), null)
    );

    when(cartRepository.findByUserId(1L)).thenReturn(Optional.of(cart));
    when(stockService.getArticleById(articleId)).thenReturn(article);
    when(stockService.getArticlesByIds(any())).thenReturn(cartArticles);

    cartUseCaseImp.addArticleToCart(articleId, quantity);

    verify(cartRepository).save(cart);
  }

  @Test
  void whenArticleIsAlreadyInCart_ShouldUpdateQuantity() {
    cart.getCartItems().add(new CartItem(1L, articleId, 2));

    when(cartRepository.findByUserId(1L)).thenReturn(Optional.of(cart));
    when(stockService.getArticleById(articleId)).thenReturn(article);

    cartUseCaseImp.addArticleToCart(articleId, quantity);

    assertEquals(5, cart.getCartItems().get(0).getQuantity());
    verify(cartRepository).save(cart);
  }

  @Test
  void whenArticleIsNotInCart_ShouldAddArticleToCart() {
    when(cartRepository.findByUserId(1L)).thenReturn(Optional.of(cart));
    when(stockService.getArticleById(articleId)).thenReturn(article);

    cartUseCaseImp.addArticleToCart(articleId, quantity);

    assertEquals(1, cart.getCartItems().size());
    assertEquals(articleId, cart.getCartItems().get(0).getArticleId());
    assertEquals(quantity, cart.getCartItems().get(0).getQuantity());
    verify(cartRepository).save(cart);
  }
}