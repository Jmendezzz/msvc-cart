package com.emazon.cart.infrastructure.adapters.out.services;

import com.emazon.cart.domain.models.*;
import com.emazon.cart.domain.ports.out.services.StockService;
import com.emazon.cart.infrastructure.adapters.out.feign.clients.StockFeignClient;
import com.emazon.cart.infrastructure.adapters.out.feign.dtos.ArticleSearchRequestDTO;
import com.emazon.cart.infrastructure.adapters.out.feign.mappers.StockFeignMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StockServiceFeignAdapter implements StockService {
  private final StockFeignClient stockFeignClient;
  private final StockFeignMapper mapper;
  @Override
  public Article getArticleById(Long articleId) {
    return mapper.toArticle(stockFeignClient.getArticleById(articleId));
  }

  @Override
  public List<Article> getArticlesByIds(List<Long> articleIds) {
    return stockFeignClient.getArticlesByIds(articleIds).stream()
            .map(mapper::toArticle)
            .toList();
  }

  @Override
  public Paginated<Article> getArticlesByCriteria(Pagination pagination, Sorting sorting, ArticleSearchCriteria articleSearchCriteria) {
    return mapper.toArticlePaginated(stockFeignClient.getArticlesByCriteria
            (
                    pagination.page(),
                    pagination.size(),
                    sorting.field(),
                    sorting.direction(),
                    articleSearchCriteria
            )
    );
  }
}
