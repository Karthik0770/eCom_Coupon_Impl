package com.monk.backend.dto.Cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatusCode;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InitCartResponseDto {
    private HttpStatusCode status;
    private int cartId;
    private String message;
}
