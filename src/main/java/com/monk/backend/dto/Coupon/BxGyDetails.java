package com.monk.backend.dto.Coupon;

import com.monk.backend.entity.CouponXProduct;
import com.monk.backend.utils.DiscountType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BxGyDetails {
    private List<CommonBxGyProductDto> xProducts;
    private List<CommonBxGyProductDto> yProducts;
}
