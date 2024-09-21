package com.emazon.cart.domain.exceptions.cart;

import com.emazon.cart.domain.exceptions.BusinessException;

import static com.emazon.cart.domain.utils.constants.cart.CartExceptionConstant.CATEGORY_LIMIT_EXCEEDED_EXCEPTION_CODE;
import static com.emazon.cart.domain.utils.constants.cart.CartExceptionConstant.CATEGORY_LIMIT_EXCEEDED_EXCEPTION_MESSAGE;

public class CategoryLimitExceededException extends BusinessException {
  public CategoryLimitExceededException(String categoryName) {
    super(String.format(CATEGORY_LIMIT_EXCEEDED_EXCEPTION_MESSAGE, categoryName), CATEGORY_LIMIT_EXCEEDED_EXCEPTION_CODE);
  }
}
