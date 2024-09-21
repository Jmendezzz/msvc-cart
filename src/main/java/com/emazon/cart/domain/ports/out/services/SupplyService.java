package com.emazon.cart.domain.ports.out.services;

import java.time.LocalDateTime;

public interface SupplyService {
  LocalDateTime getNextArticleSupplyDate(Long articleId);

}
