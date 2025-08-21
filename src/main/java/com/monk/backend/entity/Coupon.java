package com.monk.backend.entity;

import com.monk.backend.Utils.CouponType;
import com.monk.backend.Utils.DiscountType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "coupon")
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int couponId;
    private Enum<CouponType> couponType;
    private int minCartCost;
    @OneToOne
    @JoinColumn(name = "product_id", nullable = true)
    private Product product;
    private int minQuantityX;
    private int quantityY;
    private Enum<DiscountType> discountType;
    private int discountAmount;
    private Date startDate;
    private Date endDate;
}
