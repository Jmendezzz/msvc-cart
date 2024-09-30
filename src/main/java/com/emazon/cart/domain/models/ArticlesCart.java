package com.emazon.cart.domain.models;

public class ArticlesCart {
  private Paginated<Article> paginatedArticles;
  private Double totalPrice;

  public ArticlesCart(Paginated<Article> paginatedArticles, Double totalPrice) {
    this.paginatedArticles = paginatedArticles;
    this.totalPrice = totalPrice;
  }
  public ArticlesCart() {
  }

  public Paginated<Article> getPaginatedArticles() {
    return paginatedArticles;
  }

  public void setPaginatedArticles(Paginated<Article> paginatedArticles) {
    this.paginatedArticles = paginatedArticles;
  }

  public Double getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(Double totalPrice) {
    this.totalPrice = totalPrice;
  }
}
