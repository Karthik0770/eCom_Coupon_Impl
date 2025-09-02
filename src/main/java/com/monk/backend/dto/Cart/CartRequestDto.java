package com.monk.backend.dto.Cart;

import com.monk.backend.dto.Coupon.CommonProductXQuantityDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartRequestDto {
    private List<CommonProductXQuantityDto> cartProducts;
}
