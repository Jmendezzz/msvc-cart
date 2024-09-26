package com.emazon.cart.infrastructure.adapters.out.feign.clients;

import com.emazon.cart.infrastructure.adapters.out.feign.dtos.ArticleResponseDTO;
import com.emazon.cart.infrastructure.config.feign.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.emazon.cart.infrastructure.utils.constants.FeignConstant.STOCK_CLIENT;
import static com.emazon.cart.infrastructure.utils.constants.FeignConstant.STOCK_URL;

@FeignClient(name = STOCK_CLIENT, url = STOCK_URL, configuration = FeignConfig.class)
public interface StockFeignClient {
  @GetMapping("/api/v1/articles/{articleId}")
  ArticleResponseDTO getArticleById(@PathVariable Long articleId);
  @GetMapping("/api/v1/articles/batch")
  List<ArticleResponseDTO> getArticlesByIds(@RequestParam List<Long> articleIds);
}
