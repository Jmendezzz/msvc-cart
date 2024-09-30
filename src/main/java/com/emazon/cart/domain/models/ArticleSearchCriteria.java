package com.emazon.cart.domain.models;

import java.util.List;

public record ArticleSearchCriteria(
    List<Long> articleIds,
    String articleName,
    Long categoryId,
    Long brandId
) {
  public ArticleSearchCriteria withArticleIds(List<Long> articleIds) {
    return new ArticleSearchCriteria(articleIds, articleName, categoryId, brandId);
  }
}
