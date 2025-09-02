package com.monk.backend.dto.Coupon;

import com.monk.backend.dto.Cart.CartRequestDto;
import com.monk.backend.entity.Coupon;
import com.monk.backend.utils.DiscountType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductWiseDetails {
    private List<Integer> products;
    @Enumerated(EnumType.STRING)
    private DiscountType discountType;
    private Integer discountAmount;

    public static boolean isApplicable(CartRequestDto cart, Coupon coupon) {
        if (coupon.getStartDate().after(new Date())) return false;
        if (coupon.getEndDate().before(new Date())) return false;

        Set<Integer> cartProductIds = cart.getCartProducts().stream()
                .map(CommonProductXQuantityDto::getProductId)
                .collect(Collectors.toSet());

        return coupon.getProducts().stream()
                .anyMatch(p -> cartProductIds.contains(p.getProductId()));
    }
}
