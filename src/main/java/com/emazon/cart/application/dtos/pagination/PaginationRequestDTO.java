package com.emazon.cart.application.dtos.pagination;

import jakarta.validation.constraints.Min;

import static com.emazon.cart.domain.utils.constants.pagination.PaginationConstant.MIN_PAGE;
import static com.emazon.cart.domain.utils.constants.pagination.PaginationConstant.MIN_PAGE_SIZE;
import static com.emazon.cart.domain.utils.constants.pagination.PaginationExceptionConstant.INVALID_PAGE_NUMBER;
import static com.emazon.cart.domain.utils.constants.pagination.PaginationExceptionConstant.INVALID_PAGE_SIZE;

public record PaginationRequestDTO(
        @Min(value = MIN_PAGE, message = INVALID_PAGE_NUMBER)
        Integer page,
        @Min(value = MIN_PAGE_SIZE, message = INVALID_PAGE_SIZE)
        Integer size
) {
}
