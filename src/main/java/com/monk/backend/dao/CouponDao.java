package com.monk.backend.dao;

import com.monk.backend.entity.Coupon;
import com.monk.backend.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CouponDao {
    @Autowired
    CouponRepository couponRepository;

    public List<Coupon> getCoupons(){
        return couponRepository.findAll();
    }

    public Coupon createNewCoupon(Coupon coupon){
        return couponRepository.save(coupon);
    }
}
