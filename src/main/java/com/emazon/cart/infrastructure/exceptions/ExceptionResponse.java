package com.emazon.cart.infrastructure.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExceptionResponse{
  private LocalDateTime timestamp;
  private String code;
  private String message;
  private HttpStatus status;
}
