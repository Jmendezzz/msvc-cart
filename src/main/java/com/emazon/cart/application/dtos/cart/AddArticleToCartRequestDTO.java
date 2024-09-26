package com.emazon.cart.application.dtos.cart;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import static com.emazon.cart.domain.utils.constants.cart.CartConstant.MIN_QUANTITY;
import static com.emazon.cart.domain.utils.constants.cart.CartExceptionConstant.*;

public record AddArticleToCartRequestDTO(
    @NotNull(message = ARTICLE_ID_EMPTY_EXCEPTION_MESSAGE)
    Long articleId,
    @NotNull(message = QUANTITY_EMPTY_EXCEPTION_MESSAGE)
    @Min(value = MIN_QUANTITY, message = QUANTITY_EMPTY_EXCEPTION_MESSAGE)
    Integer quantity
) {
}
