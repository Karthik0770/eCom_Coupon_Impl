package com.monk.backend.dto.Coupon;

import com.monk.backend.dto.Cart.CartRequestDto;
import com.monk.backend.entity.Coupon;
import com.monk.backend.entity.CouponXProduct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BxGyDetails {
    private List<CommonProductXQuantityDto> xProducts;
    private List<CommonProductXQuantityDto> yProducts;
    private Integer repetitionLimit;

    public static Boolean isApplicable(CartRequestDto cart, Coupon coupon){
        if (coupon.getStartDate().after(new Date())) return false;
        if (coupon.getEndDate().before(new Date())) return false;

        Map<Integer, Integer> cartMap = cart.getCartProducts().stream()
                .collect(Collectors.toMap(
                        CommonProductXQuantityDto::getProductId,
                        CommonProductXQuantityDto::getQuantity
                ));

        for (CouponXProduct cxp : coupon.getXProducts()) {
            Integer productId = cxp.getProduct().getProductId();
            int requiredQty = cxp.getQuantityRequired();

            int cartQty = cartMap.getOrDefault(productId, 0);

            if (cartQty < requiredQty) {
                return false;
            }
        }

        return true;
    }
}
