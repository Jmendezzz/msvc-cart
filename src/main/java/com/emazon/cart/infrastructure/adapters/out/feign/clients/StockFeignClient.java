package com.emazon.cart.infrastructure.adapters.out.feign.clients;

import com.emazon.cart.domain.models.ArticleSearchCriteria;
import com.emazon.cart.domain.models.Paginated;
import com.emazon.cart.domain.models.Pagination;
import com.emazon.cart.domain.models.Sorting;
import com.emazon.cart.infrastructure.adapters.out.feign.dtos.ArticleResponseDTO;
import com.emazon.cart.infrastructure.config.feign.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.emazon.cart.infrastructure.utils.constants.FeignConstant.STOCK_CLIENT;
import static com.emazon.cart.infrastructure.utils.constants.FeignConstant.STOCK_URL;

@FeignClient(name = STOCK_CLIENT, url = STOCK_URL, configuration = FeignConfig.class)
public interface StockFeignClient {
  @GetMapping("/api/v1/articles/{articleId}")
  ArticleResponseDTO getArticleById(@PathVariable Long articleId);
  @GetMapping("/api/v1/articles/batch")
  List<ArticleResponseDTO> getArticlesByIds(@RequestParam List<Long> articleIds);
  @PostMapping("/api/v1/articles/search")
  Paginated<ArticleResponseDTO> getArticlesByCriteria(
          @RequestParam Integer page,
          @RequestParam Integer size,
          @RequestParam String sortBy,
          @RequestParam String direction,
          @RequestBody ArticleSearchCriteria searchCriteria);
}
