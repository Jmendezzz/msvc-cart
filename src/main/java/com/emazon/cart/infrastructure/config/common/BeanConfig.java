package com.emazon.cart.infrastructure.config.common;

import com.emazon.cart.domain.ports.in.usecases.cart.CartUseCase;
import com.emazon.cart.domain.ports.out.repositories.CartRepository;
import com.emazon.cart.domain.ports.out.services.AuthService;
import com.emazon.cart.domain.ports.out.services.StockService;
import com.emazon.cart.domain.ports.out.services.SupplyService;
import com.emazon.cart.domain.usecases.cart.CartUseCaseImp;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class BeanConfig {
  private final CartRepository repository;
  private final AuthService authService;
  private final StockService stockService;
  private final SupplyService supplyService;
  @Bean
  public CartUseCase addArticleToCartUseCase() {
    return new CartUseCaseImp(repository, authService, stockService, supplyService);
  }
}
