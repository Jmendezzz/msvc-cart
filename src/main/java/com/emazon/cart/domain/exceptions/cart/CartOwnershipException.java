package com.emazon.cart.domain.exceptions.cart;

import com.emazon.cart.domain.exceptions.BusinessException;

import static com.emazon.cart.domain.utils.constants.cart.CartExceptionConstant.CART_OWNERSHIP_EXCEPTION_CODE;
import static com.emazon.cart.domain.utils.constants.cart.CartExceptionConstant.CART_OWNERSHIP_EXCEPTION_MESSAGE;

public class CartOwnershipException extends BusinessException{
  public CartOwnershipException() {
    super(CART_OWNERSHIP_EXCEPTION_MESSAGE, CART_OWNERSHIP_EXCEPTION_CODE);
  }
}
