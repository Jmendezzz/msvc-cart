package com.emazon.cart.domain.utils.constants.cart;

public class CartExceptionConstant {
  public static final String CART_OWNERSHIP_EXCEPTION_MESSAGE = "You are not authorized to access this cart";
  public static final String CART_OWNERSHIP_EXCEPTION_CODE = "CART_OWNERSHIP_EXCEPTION";

  public static final String NOT_ENOUGH_STOCK_EXCEPTION_MESSAGE = "Not enough stock for this article, stock available: %d";
  public static final String NOT_STOCK_EXCEPTION_MESSAGE = "No stock available for this article, next stock supply: %s";
  public static final String NOT_ENOUGH_STOCK_EXCEPTION_CODE = "NOT_ENOUGH_STOCK_EXCEPTION";

  public static final String CATEGORY_LIMIT_EXCEEDED_EXCEPTION_MESSAGE = "Category limit exceeded for category: %s";
  public static final String CATEGORY_LIMIT_EXCEEDED_EXCEPTION_CODE = "CATEGORY_LIMIT_EXCEEDED_EXCEPTION";

  public static final String CART_ID_EMPTY_EXCEPTION_MESSAGE = "Cart id is empty";
  public static final String ARTICLE_ID_EMPTY_EXCEPTION_MESSAGE = "Article id is empty";
  public static final String QUANTITY_EMPTY_EXCEPTION_MESSAGE = "Quantity is empty";

  public static final String CART_ITEM_NOT_FOUND_EXCEPTION_MESSAGE = "Article not found in cart";
  public static final String CART_ITEM_NOT_FOUND_EXCEPTION_CODE = "CART_ITEM_NOT_FOUND_EXCEPTION";

  
  private CartExceptionConstant() {
  }

}
