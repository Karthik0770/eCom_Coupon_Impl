package com.monk.backend.serviceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.monk.backend.dao.CouponDao;
import com.monk.backend.dao.ProductDao;
import com.monk.backend.dto.Coupon.*;
import com.monk.backend.entity.Coupon;
import com.monk.backend.entity.CouponXProduct;
import com.monk.backend.entity.CouponYProduct;
import com.monk.backend.entity.Product;
import com.monk.backend.service.CouponService;
import com.monk.backend.utils.CouponType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    CouponDao couponDao;
    @Autowired
    ProductDao productDao;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(CouponServiceImpl.class);

    @Override
    public List<Coupon> getAllCoupons() {
        return couponDao.getCoupons();
    }

    @Override
    public Coupon createCoupon(CreateNewCouponRequest request) throws JsonProcessingException {
        logger.info("CREATE COUPON :: ENTERED");
        Coupon coupon = new Coupon();
        coupon.setName(request.getName());
        coupon.setCode(request.getCode());
        coupon.setType(request.getType());
        coupon.setStartDate(request.getStartDate());
        coupon.setEndDate(request.getEndDate());

        if (request.getType() == CouponType.PRODUCT_WISE) {
            logger.info("CREATE COUPON :: PRODUCT WISE");
            ProductWiseDetails details = objectMapper.treeToValue(request.getDetails(), ProductWiseDetails.class);

            coupon.setDiscountType(details.getDiscountType());
            coupon.setDiscountAmount(details.getDiscountAmount());

            List<Product> products = productDao.findAllById(details.getProducts());
            coupon.setProducts(products);
        }else if(request.getType() == CouponType.CART_WISE){
            logger.info("CREATE COUPON :: CART WISE");
            CartWiseDetails details = objectMapper.treeToValue(request.getDetails(), CartWiseDetails.class);

            coupon.setThresholdAmount(details.getThresholdAmount());
            coupon.setDiscountType(details.getDiscountType());
            coupon.setDiscountAmount(details.getDiscountAmount());
        }else if(request.getType() == CouponType.BXGY) {
            logger.info("CREATE COUPON :: BXGY WISE");
            BxGyDetails details = objectMapper.treeToValue(request.getDetails(), BxGyDetails.class);

            for (CommonBxGyProductDto dto : details.getXProducts()) {
                Product product = productDao.findById(dto.getProductId());

                CouponXProduct cx = new CouponXProduct();
                cx.setCoupon(coupon);
                cx.setProduct(product);
                cx.setQuantityRequired(dto.getQuantity());

                coupon.getXProducts().add(cx);
            }
            for (CommonBxGyProductDto dto : details.getYProducts()) {
                Product product = productDao.findById(dto.getProductId());

                CouponYProduct cy = new CouponYProduct();
                cy.setCoupon(coupon);
                cy.setProduct(product);
                cy.setQuantityRewarded(dto.getQuantity());

                coupon.getYProducts().add(cy);
            }
        }

        return couponDao.createNewCoupon(coupon);
    }
}
