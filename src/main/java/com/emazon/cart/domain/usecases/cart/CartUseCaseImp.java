package com.emazon.cart.domain.usecases.cart;

import com.emazon.cart.domain.exceptions.cart.CartItemNotFoundException;
import com.emazon.cart.domain.exceptions.cart.CategoryLimitExceededException;
import com.emazon.cart.domain.exceptions.cart.NotEnoughStockException;
import com.emazon.cart.domain.models.*;
import com.emazon.cart.domain.ports.in.usecases.cart.CartUseCase;
import com.emazon.cart.domain.ports.out.repositories.CartRepository;
import com.emazon.cart.domain.ports.out.services.AuthService;
import com.emazon.cart.domain.ports.out.services.StockService;
import com.emazon.cart.domain.ports.out.services.SupplyService;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.emazon.cart.domain.utils.constants.CommonConstant.ONE;
import static com.emazon.cart.domain.utils.constants.CommonConstant.ZERO;
import static com.emazon.cart.domain.utils.constants.cart.CartConstant.MAX_ARTICLES_PER_CATEGORY;

public class CartUseCaseImp implements CartUseCase {
  private final CartRepository cartRepository;
  private final AuthService authService;
  private final StockService stockService;
  private final SupplyService supplyService;

  public CartUseCaseImp(CartRepository cartRepository, AuthService authService, StockService stockService, SupplyService supplyService) {
    this.cartRepository = cartRepository;
    this.authService = authService;
    this.stockService = stockService;
    this.supplyService = supplyService;
  }
  @Override
  public void addArticleToCart(Long articleId, Integer quantity) {
    Cart cart = getUserCart(authService.getUserId());
    Article article = stockService.getArticleById(articleId);

    validateArticleStockAvailability(cart, article, quantity);
    validateCategoryLimit(cart, article);

    updateCartItems(cart, article, quantity);
  }

  @Override
  public void removeArticleFromCart(Long articleId) {
    Cart cart = getUserCart(authService.getUserId());
    Optional<CartItem> cartItem = getCartItemByArticleId(cart, articleId);

    if(cartItem.isEmpty()) throw new CartItemNotFoundException();

    cart.getCartItems().remove(cartItem.get());

    updateCart(cart);
  }

  @Override
  public ArticlesCart getArticlesCart(Sorting sorting, Pagination pagination, ArticleSearchCriteria searchCriteria) {
    Cart cart = getUserCart(authService.getUserId());

    List<Long> cartArticleIds = getCartArticleIds(cart);
    ArticleSearchCriteria cartSearchCriteria = searchCriteria.withArticleIds(cartArticleIds);

    Paginated<Article> paginatedArticles = stockService.getArticlesByCriteria(pagination, sorting, cartSearchCriteria);
    List<Article> articles = setArticlesNextSupplyDate(paginatedArticles.getData());
    paginatedArticles.setData(articles);

    Double totalPrice = calculateCartTotalPrice(cart);

    return new ArticlesCart(paginatedArticles, totalPrice);
  }

  private Cart getUserCart(Long userId) {
    return cartRepository
            .findByUserId(userId)
            .orElseGet(this::createUserCart);
  }

  private Cart createUserCart() {
    Long userId = authService.getUserId();
    Cart cart = new Cart();
    cart.setUserId(userId);
    cart.setCartItems(new ArrayList<>());
    cart.setCreatedAt(LocalDateTime.now());
    cart.setUpdatedAt(LocalDateTime.now());

    return cartRepository.save(cart);
  }

  private void validateArticleStockAvailability(Cart cart, Article article, Integer quantity) {
    Integer totalQuantityInCart = cart.getCartItems().stream()
            .filter(cartItem -> cartItem.getArticleId().equals(article.id()))
            .mapToInt(CartItem::getQuantity)
            .sum();
    totalQuantityInCart += quantity;

    if(totalQuantityInCart > article.stock()){
      Optional<LocalDateTime> nextSupplyDate = supplyService.getNextArticleSupplyDate(article.id());

      nextSupplyDate.ifPresentOrElse(date -> {
                throw new NotEnoughStockException(date);
              },
              () -> {
                throw new NotEnoughStockException();
              }
      );
    }
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
            .collect(Collectors.groupingBy(Category::id, Collectors.summingInt(category -> ONE)));
  }

  private Double calculateCartTotalPrice(Cart cart) {
    List<Article> cartArticles = getCartArticles(cart);

    return cart.getCartItems().stream()
            .mapToDouble(cartItem -> {
              Article article = cartArticles.stream()
                      .filter(a -> a.id().equals(cartItem.getArticleId()))
                      .findAny()
                      .orElseThrow();
              return article.price() * cartItem.getQuantity();
            })
            .sum();
  }
  private void updateCartItems(Cart cart, Article article, Integer quantity) {
    Optional<CartItem> existingCartItem = getCartItemByArticleId(cart, article.id());
    if(existingCartItem.isPresent()){
      CartItem cartItem = existingCartItem.get();
      cartItem.setQuantity(cartItem.getQuantity() + quantity);
    } else {
      CartItem cartItem = new CartItem();
      cartItem.setArticleId(article.id());
      cartItem.setQuantity(quantity);
      cart.getCartItems().add(cartItem);
    }

    updateCart(cart);
  }
  private List<Article> getCartArticles(Cart cart) {
    List<Long> cartArticleIds = getCartArticleIds(cart);

    return stockService.getArticlesByIds(cartArticleIds);
  }
  private List<Long> getCartArticleIds(Cart cart) {
    return cart.getCartItems().stream()
            .map(CartItem::getArticleId)
            .toList();
  }
  private Optional<CartItem> getCartItemByArticleId(Cart cart, Long articleId) {
    return cart.getCartItems().stream()
            .filter(cartItem -> cartItem.getArticleId().equals(articleId))
            .findAny();
  }
  private List<Article> setArticlesNextSupplyDate(List<Article> articles) {
    return articles.stream()
            .map(article -> Objects.equals(article.stock(), ZERO) ? article.withSupplyDate(getNextSupplyDate(article.id())) : article)
            .toList();
  }

  private LocalDateTime getNextSupplyDate(Long articleId) {
    Optional<LocalDateTime> nextSupplyDate = supplyService.getNextArticleSupplyDate(articleId);
    return nextSupplyDate.orElse(null);
  }
  private void updateCart(Cart cart) {
    cart.setUpdatedAt(LocalDateTime.now());
    cartRepository.save(cart);
  }

}
