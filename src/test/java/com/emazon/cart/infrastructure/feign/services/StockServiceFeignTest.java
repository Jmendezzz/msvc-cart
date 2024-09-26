package com.emazon.cart.infrastructure.feign.services;

import com.emazon.cart.domain.models.Article;
import com.emazon.cart.domain.models.Category;
import com.emazon.cart.infrastructure.adapters.out.feign.clients.StockFeignClient;
import com.emazon.cart.infrastructure.adapters.out.feign.dtos.ArticleResponseDTO;
import com.emazon.cart.infrastructure.adapters.out.feign.dtos.CategoryResponseDTO;
import com.emazon.cart.infrastructure.adapters.out.feign.mappers.StockFeignMapper;
import com.emazon.cart.infrastructure.adapters.out.services.StockServiceFeignAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

class StockServiceFeignTest {

  @Mock
  private StockFeignClient stockFeignClient;

  @Mock
  private StockFeignMapper stockFeignMapper;

  @InjectMocks
  private StockServiceFeignAdapter stockServiceFeignAdapter;

  private ArticleResponseDTO articleResponseDTO;
  private Article article;

  @BeforeEach
  void setUp() {
    articleResponseDTO = new ArticleResponseDTO(
            1L,
            "Test Article",
            "Test Description",
            100.0,
            10,
            null,
            Set.of(new CategoryResponseDTO(1L, "Electronics"))
    );

    article = new Article(
            1L,
            "Test Article",
            "Test Description",
            100.0,
            10,
            List.of(new Category(1L, "Electronics"))
    );
  }

  @Test
  void whenGetArticleByIdShouldReturnMappedArticle() {
    Long articleId = 1L;

    when(stockFeignClient.getArticleById(articleId)).thenReturn(articleResponseDTO);
    when(stockFeignMapper.toArticle(articleResponseDTO)).thenReturn(article);

    Article result = stockServiceFeignAdapter.getArticleById(articleId);

    assertNotNull(result);
    assertEquals(article.id(), result.id());
    assertEquals(article.name(), result.name());
    assertEquals(article.stock(), result.stock());
    verify(stockFeignClient).getArticleById(articleId);
    verify(stockFeignMapper).toArticle(articleResponseDTO);
  }

  @Test
  void whenGetArticlesByIdsShouldReturnMappedArticlesList() {
    List<Long> articleIds = List.of(1L, 2L);
    ArticleResponseDTO articleResponseDTO2 = new ArticleResponseDTO(
            2L,
            "Another Article",
            "Another Description",
            150.0,
            5,
            null,
            Set.of(new CategoryResponseDTO(2L, "Books"))
    );
    Article article2 = new Article(
            2L,
            "Another Article",
            "Another Description",
            150.0,
            5,
            List.of(new Category(2L, "Books"))
    );

    when(stockFeignClient.getArticlesByIds(articleIds)).thenReturn(List.of(articleResponseDTO, articleResponseDTO2));
    when(stockFeignMapper.toArticle(articleResponseDTO)).thenReturn(article);
    when(stockFeignMapper.toArticle(articleResponseDTO2)).thenReturn(article2);

    List<Article> result = stockServiceFeignAdapter.getArticlesByIds(articleIds);

    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals(article.id(), result.get(0).id());
    assertEquals(article2.id(), result.get(1).id());
    verify(stockFeignClient).getArticlesByIds(articleIds);
    verify(stockFeignMapper, times(2)).toArticle(any(ArticleResponseDTO.class));
  }
}
