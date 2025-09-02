package com.monk.backend.dto.Coupon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonProductXQuantityDto {
    private int productId;
    private int quantity;
}
