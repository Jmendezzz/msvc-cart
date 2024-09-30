package com.emazon.cart.domain.exceptions.cart;

import com.emazon.cart.domain.exceptions.BusinessException;

import java.time.LocalDateTime;

import static com.emazon.cart.domain.utils.constants.cart.CartExceptionConstant.*;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;

public class NotEnoughStockException extends BusinessException {
  public NotEnoughStockException(Integer stockAvailable) {
    super(String.format(NOT_ENOUGH_STOCK_EXCEPTION_MESSAGE,stockAvailable), NOT_ENOUGH_STOCK_EXCEPTION_CODE);
  }
  public NotEnoughStockException(LocalDateTime nextSupplyDate) {
    super(String.format(NOT_STOCK_EXCEPTION_MESSAGE,nextSupplyDate.format(ISO_LOCAL_DATE_TIME)), NOT_ENOUGH_STOCK_EXCEPTION_CODE);
  }
  public NotEnoughStockException() {
    super(NOT_STOCK_FOUND_EXCEPTION, NOT_ENOUGH_STOCK_EXCEPTION_CODE);
  }
}
