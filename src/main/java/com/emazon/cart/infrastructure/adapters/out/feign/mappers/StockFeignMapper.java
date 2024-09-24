package com.emazon.cart.infrastructure.adapters.out.feign.mappers;

import com.emazon.cart.domain.models.Article;
import com.emazon.cart.domain.models.Category;
import com.emazon.cart.infrastructure.adapters.out.feign.dtos.ArticleResponseDTO;
import com.emazon.cart.infrastructure.adapters.out.feign.dtos.CategoryResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface StockFeignMapper {
  @Mapping(source = "categories", target = "categories")
  Article toArticle(ArticleResponseDTO articleResponseDTO);

  default List<Category> mapCategories(Set<CategoryResponseDTO> categoryResponseDTOs) {
    return categoryResponseDTOs.stream()
            .map(this::toCategory)
            .toList();
  }

  // Helper method to map from CategoryResponseDTO to Category
  default Category toCategory(CategoryResponseDTO categoryResponseDTO) {
    return new Category(categoryResponseDTO.id(), categoryResponseDTO.name());
  }
}
