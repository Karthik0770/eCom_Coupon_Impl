package com.monk.backend.dto.Coupon;

import com.monk.backend.utils.DiscountType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartWiseDetails {
    private int thresholdAmount;
    @Enumerated(EnumType.STRING)
    private DiscountType discountType;
    private int discountAmount;
}