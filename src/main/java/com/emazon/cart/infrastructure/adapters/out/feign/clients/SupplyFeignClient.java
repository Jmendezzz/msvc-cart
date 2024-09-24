package com.emazon.cart.infrastructure.adapters.out.feign.clients;

import com.emazon.cart.infrastructure.adapters.out.feign.dtos.SupplyResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static com.emazon.cart.infrastructure.utils.constants.FeignConstant.SUPPLY_CLIENT;
import static com.emazon.cart.infrastructure.utils.constants.FeignConstant.SUPPLY_URL;

@FeignClient(name = SUPPLY_CLIENT, url = SUPPLY_URL)
public interface SupplyFeignClient {
  @GetMapping("/api/v1/supplies/next-available/{articleId}")
  SupplyResponseDTO getNextAvailableSupplyForArticle(@PathVariable Long articleId);
}
