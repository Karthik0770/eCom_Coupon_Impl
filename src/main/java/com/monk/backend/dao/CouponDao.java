package com.monk.backend.dao;

import com.monk.backend.entity.Coupon;
import com.monk.backend.repository.CouponRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CouponDao {
    @Autowired
    CouponRepository couponRepository;

    Logger logger = LoggerFactory.getLogger("logger");

    public List<Coupon> getCoupons(){
        return couponRepository.findAll();
    }

    public Coupon getCouponById(int id){
        Optional<Coupon> coupon = couponRepository.findById(id);
        return coupon.get();
    }

    public Coupon deleteCouponById(int id){
        Optional<Coupon> couponOpt = couponRepository.findById(id);
        logger.info("coupon optional : {}",couponOpt);
        if (!couponOpt.isEmpty()){
            logger.info("coupon optional is not empty");
            Coupon coupon = couponOpt.get();
            couponRepository.deleteById(id);
            return coupon;
        }
        logger.info("coupon optional is empty");
        return null;
    }

    public Coupon createNewCoupon(Coupon coupon){
        return couponRepository.save(coupon);
    }


}
