package com.emazon.cart.domain.usecases.cart;

import com.emazon.cart.domain.exceptions.cart.CartOwnershipException;
import com.emazon.cart.domain.exceptions.cart.CategoryLimitExceededException;
import com.emazon.cart.domain.exceptions.cart.NotEnoughStockException;
import com.emazon.cart.domain.models.Article;
import com.emazon.cart.domain.models.Cart;
import com.emazon.cart.domain.models.CartItem;
import com.emazon.cart.domain.models.Category;
import com.emazon.cart.domain.ports.in.usecases.cart.AddArticleToCartUseCase;
import com.emazon.cart.domain.ports.out.repositories.CartRepository;
import com.emazon.cart.domain.ports.out.services.AuthService;
import com.emazon.cart.domain.ports.out.services.StockService;
import com.emazon.cart.domain.ports.out.services.SupplyService;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.emazon.cart.domain.utils.constants.CommonConstant.ONE;
import static com.emazon.cart.domain.utils.constants.cart.CartConstant.MAX_ARTICLES_PER_CATEGORY;
import static org.hibernate.type.descriptor.java.IntegerJavaType.ZERO;

public class AddArticleToCartUseCaseImp implements AddArticleToCartUseCase {
  private final CartRepository repository;
  private final AuthService authService;
  private final StockService stockService;
  private final SupplyService supplyService;

  public AddArticleToCartUseCaseImp(CartRepository cartRepository, AuthService authService, StockService stockService, SupplyService supplyService) {
    this.repository = cartRepository;
    this.authService = authService;
    this.stockService = stockService;
    this.supplyService = supplyService;
  }
  @Override
  public void addArticleToCart(Long cartId, Long articleId, Integer quantity) {
    Cart cart = getCart(cartId);
    validateCartOwnership(cart);

    Article article = stockService.getArticleById(articleId);
    validateArticleStock(article, quantity);
    validateCategoryLimit(cart, article);

    updateCart(cart, article, quantity);
  }

  private Cart getCart(Long cartId) {
    return repository
            .findById(cartId)
            .orElseGet(this::createUserCart);
  }

  private Cart createUserCart() {
    Long userId = authService.getUserId();
    Cart cart = new Cart();
    cart.setUserId(userId);
    cart.setCartItems(new ArrayList<>());
    cart.setCreatedAt(LocalDateTime.now());
    cart.setUpdatedAt(LocalDateTime.now());

    return cart;
  }

  private void validateCartOwnership(Cart cart) {
    if(!cart.getUserId().equals(authService.getUserId())) throw new CartOwnershipException();
  }

  private void validateArticleStock(Article article, Integer quantity) {
    Integer articleStock = article.stock();
    if(articleStock.equals(ZERO)){
      LocalDateTime nextSupplyDate = supplyService.getNextArticleSupplyDate(article.id());
      throw new NotEnoughStockException(nextSupplyDate);
    }
    if(articleStock < quantity) throw new NotEnoughStockException(articleStock);
  }

  private void validateCategoryLimit(Cart cart, Article article) {
    Map<Long, Integer> cartCategories = getCartCategoriesGrouped(cart);

    article.categories().stream()
            .filter(category -> cartCategories.getOrDefault(category.id(), ZERO) + ONE > MAX_ARTICLES_PER_CATEGORY)
            .findAny()
            .ifPresent(category -> {
              throw new CategoryLimitExceededException(category.name());
            });

  }
  private Map<Long, Integer> getCartCategoriesGrouped(Cart cart) {
    List<Article> cartArticles = getCartArticles(cart);

    return  cartArticles.stream()
            .flatMap(article -> article.categories().stream())
            .collect(Collectors.groupingBy(Category::id, Collectors.summingInt(category -> 1)));
  }
  private List<Article> getCartArticles(Cart cart) {
    List<Long> cartArticleIds = cart.getCartItems().stream()
            .map(CartItem::getArticleId)
            .toList();

    return stockService.getArticlesByIds(cartArticleIds);
  }
  private void updateCart(Cart cart, Article article, Integer quantity) {
    Optional<CartItem> existingCartItem = cart.getCartItems().stream()
            .filter(cartItem -> cartItem.getArticleId().equals(article.id()))
            .findAny();

    if(existingCartItem.isPresent()){
      CartItem cartItem = existingCartItem.get();
      cartItem.setQuantity(cartItem.getQuantity() + quantity);
    } else {
      CartItem cartItem = new CartItem();
      cartItem.setArticleId(article.id());
      cartItem.setQuantity(quantity);
      cart.getCartItems().add(cartItem);
    }

    cart.setUpdatedAt(LocalDateTime.now());
    repository.save(cart);
  }

}
