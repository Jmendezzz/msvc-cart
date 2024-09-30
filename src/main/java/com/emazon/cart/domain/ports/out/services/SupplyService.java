package com.emazon.cart.domain.ports.out.services;

import java.time.LocalDateTime;
import java.util.Optional;

public interface SupplyService {
  Optional<LocalDateTime> getNextArticleSupplyDate(Long articleId);

}
