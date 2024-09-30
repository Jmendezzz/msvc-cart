package com.emazon.cart.application.mappers;

import com.emazon.cart.application.dtos.pagination.PaginationRequestDTO;
import com.emazon.cart.domain.models.Pagination;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaginationMapperDTO {
  Pagination toDomain(PaginationRequestDTO paginationDTO);
}
