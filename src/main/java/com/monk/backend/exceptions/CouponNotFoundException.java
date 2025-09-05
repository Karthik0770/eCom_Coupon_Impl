package com.monk.backend.exceptions;

public class CouponNotFoundException extends RuntimeException {
    public CouponNotFoundException(Integer couponId) {
        super("Coupon not found - "+ couponId);
    }
}
