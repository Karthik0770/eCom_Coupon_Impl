package com.monk.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.monk.backend.dto.Coupon.CreateNewCouponRequest;
import com.monk.backend.entity.Coupon;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CouponService {
    public List<Coupon> getAllCoupons();
    public Coupon createCoupon(CreateNewCouponRequest request) throws JsonProcessingException;
}
