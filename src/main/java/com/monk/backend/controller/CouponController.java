package com.monk.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.monk.backend.dto.Cart.CartRequestDto;
import com.monk.backend.dto.Coupon.CreateNewCouponRequest;
import com.monk.backend.dto.Coupon.DiscountObjectDto;
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
        try{
            List<Coupon> coupons = couponService.getAllCoupons();
            response.setPayload(coupons);
        }catch(Exception e){
            response.setStatus("FAILURE");
            response.setMessage(e.getMessage());
            response.setPayload(null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        response.setStatus("SUCCESS");
        response.setMessage("All coupons fetched!");
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
    public ResponseEntity<ResponseDto<List<DiscountObjectDto>>> getApplicableCouponsForCart(@RequestBody CartRequestDto cart){
        ResponseDto<List<DiscountObjectDto>> response = new ResponseDto<>();
        try{
            List<DiscountObjectDto> applicableCoupons = couponService.getApplicableCouponsForCart(cart);
            response.setPayload(applicableCoupons);
        }catch (Exception e){
            response.setStatus("FAILURE");
            response.setMessage(e.getMessage());
            response.setPayload(null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        response.setStatus("SUCCESS");
        response.setMessage("Applicable coupons fetched!");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("apply-coupon/{couponId}")
    public ResponseEntity<ResponseDto<DiscountObjectDto>> applyCouponOnCart(@RequestBody CartRequestDto cartRequestDto, @PathVariable Integer couponId){
        ResponseDto<DiscountObjectDto> response = new ResponseDto<>();
        try {
            DiscountObjectDto discountObject = couponService.applyCouponOnCart(cartRequestDto,couponId);
            response.setPayload(discountObject);
        }catch (Exception e){
            response.setStatus("FAILURE");
            response.setMessage(e.getMessage());
            response.setPayload(null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        response.setStatus("SUCCESS");
        response.setMessage("Coupon application details fetched!");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

}
