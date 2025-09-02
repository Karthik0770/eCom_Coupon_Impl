package com.monk.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.monk.backend.dto.Cart.CartRequestDto;
import com.monk.backend.dto.Coupon.CreateNewCouponRequest;
import com.monk.backend.dto.ResponseDto;
import com.monk.backend.entity.Coupon;
import com.monk.backend.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1")
public class CouponController {

    @Autowired
    CouponService couponService;

    @PostMapping("coupons")
    public ResponseEntity<ResponseDto<Coupon>> createNewCoupon(@RequestBody CreateNewCouponRequest cr) throws JsonProcessingException {
        ResponseDto<Coupon> response = new ResponseDto();
        Coupon newCoupon = null;
        try{
            newCoupon = couponService.createCoupon(cr,null);
        }catch(Exception e){
            response.setStatus("FAILURE");
            response.setMessage(e.getMessage());
            response.setPayload(null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        response.setPayload(newCoupon);
        response.setStatus("SUCCESS");
        response.setMessage("New coupon created successfully!");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("coupons")
    public ResponseEntity<ResponseDto<List<Coupon>>> getAllCoupons(){
        ResponseDto<List<Coupon>> response = new ResponseDto();
        List<Coupon> coupons = null;
        try{
            coupons = couponService.getAllCoupons();
        }catch(Exception e){
            response.setStatus("FAILURE");
            response.setMessage(e.getMessage());
            response.setPayload(null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        response.setStatus("SUCCESS");
        response.setMessage("All coupons fetched!");
        response.setPayload(coupons);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("coupons/{id}")
    public ResponseEntity<Coupon> getCoupon(@PathVariable int id){
        return new ResponseEntity<>(couponService.getCouponById(id),HttpStatus.OK);
    }

    @PutMapping("coupons/{id}")
    public ResponseEntity<Coupon> updateCoupon(@PathVariable int id, @RequestBody CreateNewCouponRequest couponReq) throws JsonProcessingException {
        return new ResponseEntity<>(couponService.createCoupon(couponReq, id),HttpStatus.OK);
    }

    @DeleteMapping("coupons/{id}")
    public ResponseEntity<Coupon> deleteCoupon(@PathVariable int id){
        return new ResponseEntity<>(couponService.deleteCouponByID(id),HttpStatus.OK);
    }

    @PostMapping("applicable-coupons")
    public ResponseEntity<?> getApplicableCouponsForCart(@RequestBody CartRequestDto cart){
        return new ResponseEntity<>(couponService.getApplicableCouponsForCart(cart),HttpStatus.OK);
    }

}
