package com.emazon.cart.infrastructure.adapters.out.services;

import com.emazon.cart.domain.ports.out.services.SupplyService;
import com.emazon.cart.infrastructure.adapters.out.feign.clients.SupplyFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class SupplyServiceFeignAdapter implements SupplyService {
  private final SupplyFeignClient supplyFeignClient;
  @Override
  public LocalDateTime getNextArticleSupplyDate(Long articleId) {
    return supplyFeignClient.getNextAvailableSupplyForArticle(articleId).availableAt();
  }
}
