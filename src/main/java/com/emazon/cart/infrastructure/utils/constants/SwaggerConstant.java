package com.emazon.cart.infrastructure.utils.constants;

public class SwaggerConstant {
  public static final String API_TITLE = "Transactions Microservice API";
  public static final String API_DESCRIPTION = "Transactions API Documentation contains all the endpoints to manage transactions in the system.";
  public static final String API_VERSION = "1.0.0";
  public static final String CONTACT_NAME = "Juan Gerardo Méndez López";
  public static final String CONTACT_EMAIL = "juange.mendez.lopez@gmail.com";
  public static final String SERVER_URL = "http://localhost:8092";
  public static final String SERVER_DESCRIPTION = "Local Dev Server";
  public static final String SECURITY_NAME = "JWT Token";
  public static final String SCHEME = "bearer";
  public static final String BEARER_FORMAT = "JWT";
  public static final String ADD_ARTICLE_TO_CART_OPERATION = "Add an article to a cart";
  public static final String ADD_ARTICLE_TO_CART_NOTE = "This endpoint allows you to add an article to a cart";
  public static final String CART_ID_PARAM = "ID of the cart";
  public static final String ARTICLE_REQUEST_PARAM = "Request body for adding an article to a cart";
  public static final String SUCCESS_RESPONSE = "Successfully added the article to the cart";
  public static final String HTTP_STATUS_200 = "200";
  public static final String REMOVE_ARTICLE_SUMMARY = "Remove an article from a cart";
  public static final String REMOVE_ARTICLE_DESC = "This endpoint allows you to remove an article from a cart";
  public static final String ARTICLE_ID_PARAM_DESC = "ID of the article to be removed from the cart";


  private SwaggerConstant() {
  }
}
