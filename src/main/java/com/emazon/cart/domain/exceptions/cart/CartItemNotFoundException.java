package com.emazon.cart.domain.exceptions.cart;

import com.emazon.cart.domain.exceptions.BusinessException;

import static com.emazon.cart.domain.utils.constants.cart.CartExceptionConstant.CART_ITEM_NOT_FOUND_EXCEPTION_CODE;
import static com.emazon.cart.domain.utils.constants.cart.CartExceptionConstant.CART_ITEM_NOT_FOUND_EXCEPTION_MESSAGE;

public class CartItemNotFoundException extends BusinessException{

  public CartItemNotFoundException() {
    super(CART_ITEM_NOT_FOUND_EXCEPTION_MESSAGE, CART_ITEM_NOT_FOUND_EXCEPTION_CODE);
  }
}
