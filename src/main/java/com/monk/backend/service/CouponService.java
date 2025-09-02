package com.monk.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.monk.backend.dto.Coupon.CreateNewCouponRequest;
import com.monk.backend.entity.Coupon;

import java.util.List;

public interface CouponService {
    public List<Coupon> getAllCoupons();
    public Coupon deleteCouponByID(int id);
    public Coupon updateCouponById(int id, CreateNewCouponRequest coupon);
    public Coupon getCouponById(int id);
    public Coupon createCoupon(CreateNewCouponRequest request, Integer id) throws JsonProcessingException;
}
