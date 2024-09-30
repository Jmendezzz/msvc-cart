package com.emazon.cart.infrastructure.feign.services;

import com.emazon.cart.infrastructure.adapters.out.feign.clients.SupplyFeignClient;
import com.emazon.cart.infrastructure.adapters.out.feign.dtos.SupplyResponseDTO;
import com.emazon.cart.infrastructure.adapters.out.services.SupplyServiceFeignAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SupplyServiceFeignTest {
  @Mock
  private SupplyFeignClient supplyFeignClient;

  @InjectMocks
  private SupplyServiceFeignAdapter supplyServiceFeignAdapter;

  private SupplyResponseDTO supplyResponseDTO;

  @BeforeEach
  void setUp() {
    supplyResponseDTO = new SupplyResponseDTO(
            1L,
            100L,
            50,
            1L,
            LocalDateTime.now(),
            LocalDateTime.now().plusDays(5)
    );
  }

  @Test
  void whenGetNextArticleSupplyDateShouldReturnCorrectDate() {
    Long articleId = 100L;
    LocalDateTime expectedSupplyDate = supplyResponseDTO.availableAt();

    when(supplyFeignClient.getNextAvailableSupplyForArticle(articleId)).thenReturn(supplyResponseDTO);

    Optional<LocalDateTime> result = supplyServiceFeignAdapter.getNextArticleSupplyDate(articleId);

    assertNotNull(result);
    assertEquals(expectedSupplyDate, result.get());
    verify(supplyFeignClient).getNextAvailableSupplyForArticle(articleId);
  }

}
