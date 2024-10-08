package com.emazon.cart.infrastructure.adapters.out.feign.mappers;

import com.emazon.cart.domain.models.Article;
import com.emazon.cart.domain.models.Category;
import com.emazon.cart.domain.models.Paginated;
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
  Paginated<Article> toArticlePaginated(Paginated<ArticleResponseDTO> articleResponseDTOPaginated);

  default List<Category> mapCategories(Set<CategoryResponseDTO> categoryResponseDTOs) {
    return categoryResponseDTOs.stream()
            .map(this::toCategory)
            .toList();
  }

  default Category toCategory(CategoryResponseDTO categoryResponseDTO) {
    return new Category(categoryResponseDTO.id(), categoryResponseDTO.name());
  }
}
