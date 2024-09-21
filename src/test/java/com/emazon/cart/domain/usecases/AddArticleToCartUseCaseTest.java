package com.emazon.cart.domain.usecases;

import com.emazon.cart.domain.exceptions.cart.CartOwnershipException;
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
import com.emazon.cart.domain.usecases.cart.AddArticleToCartUseCaseImp;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddArticleToCartUseCaseTest {
  @Mock
  private CartRepository cartRepository;

  @Mock
  private AuthService authService;

  @Mock
  private StockService stockService;

  @Mock
  private SupplyService supplyService;

  @InjectMocks
  private AddArticleToCartUseCaseImp useCase;
  private Cart cart;
  private Article article;
  private final Long cartId = 1L;
  private final Long articleId = 2L;
  private final Integer quantity = 3;

  @BeforeEach
  void setUp() {

    cart = new Cart();
    cart.setUserId(1L);
    List<CartItem> cartItems = new ArrayList<>();
    cart.setCartItems(cartItems);
    cart.setCreatedAt(LocalDateTime.now());
    cart.setUpdatedAt(LocalDateTime.now());

    article = new Article(
            articleId,
            "Article",
            "Description",
            10.0,
            10,
            List.of(new Category(1L, "Category"))
    );
    when(authService.getUserId()).thenReturn(1L);
  }

  @Test
  void whenCartExistsAndOwnershipIsValid_ShouldAddArticleToCart() {
    when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
    when(stockService.getArticleById(articleId)).thenReturn(article);

    useCase.addArticleToCart(cartId, articleId, quantity);

    verify(cartRepository).save(cart);
  }
  @Test
  void whenCartDoesNotExist_ShouldCreateNewCartAndAddArticle() {
    when(cartRepository.findById(cartId)).thenReturn(Optional.empty());
    when(stockService.getArticleById(articleId)).thenReturn(article);
    when(cartRepository.save(any(Cart.class))).thenReturn(cart); // Mock the save operation for new cart creation

    useCase.addArticleToCart(cartId, articleId, quantity);

    verify(cartRepository, times(1)).save(any(Cart.class)); // Ensure save is called only once
  }

  @Test
  void whenCartOwnershipIsInvalid_ShouldThrowCartOwnershipException() {
    cart.setUserId(2L);
    when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));

    assertThrows(CartOwnershipException.class, () -> useCase.addArticleToCart(cartId, articleId, quantity));
  }

  @Test
  void whenArticleStockIsZero_ShouldThrowNotEnoughStockException() {
    article = new Article(
            articleId,
            "Article",
            "Description",
            10.0,
            0,
            List.of(new Category(1L, "Category"))
    );
    when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
    when(stockService.getArticleById(articleId)).thenReturn(article);
    when(supplyService.getNextArticleSupplyDate(articleId)).thenReturn(LocalDateTime.now().plusDays(15));

    assertThrows(NotEnoughStockException.class, () -> useCase.addArticleToCart(cartId, articleId, quantity));
  }

  @Test
  void whenArticleStockIsLessThanQuantity_ShouldThrowNotEnoughStockException() {
    article = new Article(
            articleId,
            "Article",
            "Description",
            10.0,
            2,
            List.of(new Category(1L, "Category"))
    );
    when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
    when(stockService.getArticleById(articleId)).thenReturn(article);

    assertThrows(NotEnoughStockException.class, () -> useCase.addArticleToCart(cartId, articleId, quantity));
  }

  @Test
  void whenCategoryLimitIsExceeded_ShouldThrowCategoryLimitExceededException() {
    List<Article> cartArticles = List.of(
            new Article(
                    3L,
                    "Article",
                    "Description",
                    10.0,
                    2,
                    List.of(new Category(1L, "Category"), new Category(2L, "Category2"))
            ),
            new Article(
                    4L,
                    "Article",
                    "Description",
                    10.0,
                    2,
                    List.of(new Category(1L, "Category"), new Category(3L, "Category3"))
            ),
            new Article(
                    5L,
                    "Article",
                    "Description",
                    10.0,
                    2,
                    List.of(new Category(1L, "Category"), new Category(4L, "Category4"))
            )
    );


    when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
    when(stockService.getArticleById(articleId)).thenReturn(article);
    when(stockService.getArticlesByIds(any())).thenReturn(cartArticles);


    assertThrows(CategoryLimitExceededException.class, () -> useCase.addArticleToCart(cartId, articleId, quantity));
  }

  @Test
  void whenCategoryLimitIsNotExceeded_ShouldAddArticleToCart() {
    List<Article> cartArticles = List.of(
            new Article(
                    3L,
                    "Article",
                    "Description",
                    10.0,
                    2,
                    List.of(new Category(1L, "Category"), new Category(2L, "Category2"))
            ),
            new Article(
                    4L,
                    "Article",
                    "Description",
                    10.0,
                    2,
                    List.of(new Category(1L, "Category"), new Category(3L, "Category3"))
            )
    );

    when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
    when(stockService.getArticleById(articleId)).thenReturn(article);
    when(stockService.getArticlesByIds(any())).thenReturn(cartArticles);

    useCase.addArticleToCart(cartId, articleId, quantity);

    verify(cartRepository).save(cart);
  }

  @Test
  void whenArticleIsAlreadyInCart_ShouldUpdateQuantity() {
    cart.getCartItems().add(new CartItem(1L,articleId, 2));

    when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
    when(stockService.getArticleById(articleId)).thenReturn(article);

    useCase.addArticleToCart(cartId, articleId, quantity);


    assertEquals(5, cart.getCartItems().get(0).getQuantity());
    verify(cartRepository).save(cart);
  }

  @Test
  void whenArticleIsNotInCart_ShouldAddArticleToCart() {
    when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
    when(stockService.getArticleById(articleId)).thenReturn(article);

    useCase.addArticleToCart(cartId, articleId, quantity);

    assertEquals(1, cart.getCartItems().size());
    assertEquals(articleId, cart.getCartItems().get(0).getArticleId());
    assertEquals(quantity, cart.getCartItems().get(0).getQuantity());
    verify(cartRepository).save(cart);
  }
}
