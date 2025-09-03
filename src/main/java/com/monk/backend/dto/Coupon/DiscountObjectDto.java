package com.monk.backend.dto.Coupon;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.monk.backend.utils.CouponType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DiscountObjectDto {
    private Integer couponId;
    private String code;
    @Enumerated(EnumType.STRING)
    private CouponType type;
    private Integer originalCartAmount;
    private Integer discountAmount;
    private Integer finalCartAmount;
    private String message;
}
