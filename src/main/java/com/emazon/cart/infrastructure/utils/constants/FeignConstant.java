package com.emazon.cart.infrastructure.utils.constants;

public class FeignConstant {
  public static final String USER_CLIENT = "msvc-users";
  public static final String STOCK_CLIENT = "msvc-stock";
  public static final String SUPPLY_CLIENT = "msvc-supply";
  public static final String SUPPLY_URL = "http://localhost:8092";
  public static final String USER_URL = "http://localhost:8091";
  public static final String STOCK_URL = "http://localhost:8090";
  public static final String FEIGN_ERROR_MESSAGE = "An error occurred while trying to communicate with the service";

  private FeignConstant() {
  }
}
