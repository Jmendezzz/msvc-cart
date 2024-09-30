package com.emazon.cart.infrastructure.adapters.out.services;

import com.emazon.cart.domain.ports.out.services.SupplyService;
import com.emazon.cart.infrastructure.adapters.out.feign.clients.SupplyFeignClient;
import com.emazon.cart.infrastructure.adapters.out.feign.dtos.SupplyResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SupplyServiceFeignAdapter implements SupplyService {
  private final SupplyFeignClient supplyFeignClient;
  @Override
  public Optional<LocalDateTime> getNextArticleSupplyDate(Long articleId) {
    SupplyResponseDTO response = supplyFeignClient.getNextAvailableSupplyForArticle(articleId);
    return response != null ? Optional.ofNullable(response.availableAt()) : Optional.empty();
  }
}
