package com.emazon.cart.infrastructure.adapters.in.controllers;

import com.emazon.cart.application.dtos.cart.AddArticleToCartRequestDTO;
import com.emazon.cart.application.dtos.common.ResponseDTO;
import com.emazon.cart.application.handlers.CartHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.emazon.cart.infrastructure.utils.constants.SecurityConstant.CUSTOMER_ROLE;
import static com.emazon.cart.infrastructure.utils.constants.SwaggerConstant.*;
import static com.emazon.cart.infrastructure.utils.constants.SwaggerConstant.SECURITY_NAME;

@RestController
@RequestMapping("/api/v1/cart")
@AllArgsConstructor
public class CartController {
  private final CartHandler cartHandler;

  @Operation(
          summary = ADD_ARTICLE_TO_CART_OPERATION,
          description = ADD_ARTICLE_TO_CART_NOTE,
          responses = {
                  @ApiResponse(responseCode = HTTP_STATUS_200, description = SUCCESS_RESPONSE, content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
          },
          parameters = {
                  @Parameter(description = CART_ID_PARAM, required = true),
                  @Parameter(description = ARTICLE_REQUEST_PARAM, required = true, schema = @Schema(implementation = AddArticleToCartRequestDTO.class))
          },
          security = @SecurityRequirement(name = SECURITY_NAME)
  )
  @PostMapping("/add-article")
  @PreAuthorize("hasRole('" + CUSTOMER_ROLE + "')")
  public ResponseEntity<ResponseDTO> addArticleToCart(
          @Valid @RequestBody AddArticleToCartRequestDTO addArticleToCartRequestDTO
          ) {
    return new ResponseEntity<>(
            cartHandler.addArticleToCart(addArticleToCartRequestDTO),
            HttpStatus.OK
    );
  }
}