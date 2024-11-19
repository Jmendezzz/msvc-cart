package com.emazon.cart.domain.models;

public class ArticlesCart {
  private Paginated<CartArticleItem> paginatedArticles;
  private Double totalPrice;

  public ArticlesCart(Paginated<CartArticleItem> paginatedArticles, Double totalPrice) {
    this.paginatedArticles = paginatedArticles;
    this.totalPrice = totalPrice;
  }
  public ArticlesCart() {
  }

  public Paginated<CartArticleItem> getPaginatedArticles() {
    return paginatedArticles;
  }

  public void setPaginatedArticles(Paginated<CartArticleItem> paginatedArticles) {
    this.paginatedArticles = paginatedArticles;
  }

  public Double getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(Double totalPrice) {
    this.totalPrice = totalPrice;
  }
}
