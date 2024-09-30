package com.emazon.cart.application.mappers;

import com.emazon.cart.application.dtos.searchcriteria.ArticleSearchCriteriaRequestDTO;
import com.emazon.cart.domain.models.ArticleSearchCriteria;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SearchCriteriaMapperDTO {
  ArticleSearchCriteria toDomain(ArticleSearchCriteriaRequestDTO searchCriteriaDTO);
}
