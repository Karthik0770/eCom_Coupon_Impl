package com.monk.backend.dto.Coupon;

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
public class ProductWiseDetails {
    private List<Integer> products;
    @Enumerated(EnumType.STRING)
    private DiscountType discountType;
    private int discountAmount;
}
