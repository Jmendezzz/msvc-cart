package com.emazon.cart.infrastructure.feign.mappers;

import com.emazon.cart.domain.models.Article;
import com.emazon.cart.domain.models.Category;
import com.emazon.cart.infrastructure.adapters.out.feign.dtos.ArticleResponseDTO;
import com.emazon.cart.infrastructure.adapters.out.feign.dtos.CategoryResponseDTO;
import com.emazon.cart.infrastructure.adapters.out.feign.mappers.StockFeignMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StockFeignMapperTest {
  private StockFeignMapperImpl stockFeignMapper;

  @BeforeEach
  void setUp() {
    stockFeignMapper = new StockFeignMapperImpl();
  }

  @Test
  void whenArticleResponseDTOIsValidShouldMapToArticle() {
    CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO(1L, "Electronics");
    ArticleResponseDTO articleResponseDTO = new ArticleResponseDTO(
            1L,
            "Test Article",
            "Test Description",
            100.0,
            10,
            null,
            Set.of(categoryResponseDTO)
    );

    Article article = stockFeignMapper.toArticle(articleResponseDTO);

    assertNotNull(article);
    assertEquals(articleResponseDTO.id(), article.id());
    assertEquals(articleResponseDTO.name(), article.name());
    assertEquals(articleResponseDTO.description(), article.description());
    assertEquals(articleResponseDTO.price(), article.price());
    assertEquals(articleResponseDTO.stock(), article.stock());
    assertNotNull(article.categories());
    assertEquals(1, article.categories().size());
    assertEquals(categoryResponseDTO.id(), article.categories().get(0).id());
    assertEquals(categoryResponseDTO.name(), article.categories().get(0).name());
  }

  @Test
  void whenArticleResponseDTOIsNullShouldReturnNull() {
    Article article = stockFeignMapper.toArticle(null);

    assertNull(article);
  }

  @Test
  void whenCategoryResponseDTOIsValidShouldMapToCategory() {
    CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO(1L, "Electronics");

    Category category = stockFeignMapper.toCategory(categoryResponseDTO);

    assertNotNull(category);
    assertEquals(categoryResponseDTO.id(), category.id());
    assertEquals(categoryResponseDTO.name(), category.name());
  }

  @Test
  void whenCategoryResponseDTOsIsNullShouldReturnEmptyList() {
    List<Category> categories = stockFeignMapper.mapCategories(null);

    assertNotNull(categories);
    assertTrue(categories.isEmpty());
  }

  @Test
  void whenCategoryResponseDTOsIsValidShouldMapToCategoryList() {
    CategoryResponseDTO categoryResponseDTO1 = new CategoryResponseDTO(1L, "Electronics");
    CategoryResponseDTO categoryResponseDTO2 = new CategoryResponseDTO(2L, "Books");

    List<Category> categories = stockFeignMapper.mapCategories(Set.of(categoryResponseDTO1, categoryResponseDTO2));

    assertNotNull(categories);
    assertEquals(2, categories.size());

    Category category1 = categories.get(0);
    Category category2 = categories.get(1);

    assertEquals(categoryResponseDTO1.id(), category1.id());
    assertEquals(categoryResponseDTO1.name(), category1.name());

    assertEquals(categoryResponseDTO2.id(), category2.id());
    assertEquals(categoryResponseDTO2.name(), category2.name());
  }
}
