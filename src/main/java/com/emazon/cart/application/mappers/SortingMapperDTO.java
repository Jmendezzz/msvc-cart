package com.emazon.cart.application.mappers;

import com.emazon.cart.application.dtos.sorting.SortingRequestDTO;
import com.emazon.cart.domain.models.Sorting;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SortingMapperDTO {
  Sorting toDomain(SortingRequestDTO sortingDTO);
}
