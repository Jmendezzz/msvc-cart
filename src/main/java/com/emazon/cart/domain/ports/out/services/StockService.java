package com.emazon.cart.domain.ports.out.services;

import com.emazon.cart.domain.models.*;

import java.util.List;

public interface StockService {
  Article getArticleById(Long articleId);
  List<Article> getArticlesByIds(List<Long> articleIds);
  Paginated<Article> getArticlesByCriteria(Pagination pagination, Sorting sorting, ArticleSearchCriteria articleSearchCriteria);
}
