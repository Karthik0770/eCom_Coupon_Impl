package com.monk.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.monk.backend.dto.Coupon.CreateNewCouponRequest;
import com.monk.backend.entity.Coupon;
import com.monk.backend.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("coupon")
public class CouponController {

    @Autowired
    CouponService couponService;

    @PostMapping("createCoupon")
    public ResponseEntity<Coupon> createNewCoupon(@RequestBody CreateNewCouponRequest cr) throws JsonProcessingException {
        return new ResponseEntity<>(couponService.createCoupon(cr), HttpStatus.CREATED);
    }
}
