package com.emazon.cart.infrastructure.exceptions.feign;

import static com.emazon.cart.infrastructure.utils.constants.FeignConstant.FEIGN_ERROR_MESSAGE;

public class InternalFeignException extends RuntimeException{
    public InternalFeignException() {
        super(FEIGN_ERROR_MESSAGE);
    }
}
