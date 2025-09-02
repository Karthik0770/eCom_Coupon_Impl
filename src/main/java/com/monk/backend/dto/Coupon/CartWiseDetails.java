package com.monk.backend.dto.Coupon;

import com.monk.backend.dao.ProductDao;
import com.monk.backend.dto.Cart.CartRequestDto;
import com.monk.backend.entity.Coupon;
import com.monk.backend.entity.Product;
import com.monk.backend.repository.ProductRepository;
import com.monk.backend.utils.DiscountType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartWiseDetails {
    private Integer thresholdAmount;
    @Enumerated(EnumType.STRING)
    private DiscountType discountType;
    private Integer discountAmount;

    public static boolean isApplicable(CartRequestDto cart, Coupon coupon, ProductDao productDao) {
        if (coupon.getStartDate().after(new Date())) return false;
        if (coupon.getEndDate().before(new Date())) return false;
        int cartTotal = 0;
        for (CommonProductXQuantityDto cp : cart.getCartProducts()) {
            Product product = productDao.findById(cp.getProductId());

            cartTotal += product.getPrice() * cp.getQuantity();
        }

        return cartTotal >= coupon.getThresholdAmount();
    }
}