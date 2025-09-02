package com.monk.backend.dto.Coupon;

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

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartWiseDetails {
    private Integer thresholdAmount;
    @Enumerated(EnumType.STRING)
    private DiscountType discountType;
    private Integer discountAmount;

    public static boolean isApplicable(CartRequestDto cart, Coupon coupon, ProductRepository productRepository) {
        int cartTotal = 0;

        for (CommonProductXQuantityDto cp : cart.getCartProducts()) {
            Product product = productRepository.findById(cp.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + cp.getProductId()));

            cartTotal += product.getPrice() * cp.getQuantity();
        }

        return cartTotal >= coupon.getThresholdAmount();
    }
}