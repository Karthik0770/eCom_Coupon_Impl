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
import com.monk.backend.exceptions.FieldEmptyOrNullException;
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
    public Coupon getCouponById(int id) {
        return couponDao.getCouponById(id);
    }

    @Override
    public Coupon deleteCouponByID(int id) {
        Coupon deletedCoupon = couponDao.deleteCouponById(id);
        return deletedCoupon;
    }

    @Override
    public Coupon updateCouponById(int id, CreateNewCouponRequest couponReq) {
        Coupon couponFromDb = couponDao.getCouponById(id);
        if(couponReq.getName()!=null
                && !couponReq.getName().isEmpty()
                && couponReq.getName()!=couponFromDb.getName())
            couponFromDb.setName(couponReq.getName());
        if(couponReq.getType()!=null
                && couponReq.getType()!=couponFromDb.getType())
            couponFromDb.setType(couponReq.getType());
        if(couponReq.getCode()!=null
                && !couponReq.getCode().isEmpty()
                && couponReq.getCode()!=couponFromDb.getCode())
            couponFromDb.setCode(couponReq.getCode());
        if(couponReq.getStartDate()!=null
                && couponReq.getStartDate()!=couponFromDb.getStartDate())
            couponFromDb.setStartDate(couponReq.getStartDate());

        return couponDao.createNewCoupon(couponFromDb);
    }

    @Override
    public Coupon createCoupon(CreateNewCouponRequest request, Integer id) throws JsonProcessingException {
        logger.info("CREATE COUPON :: ENTERED");
        Coupon coupon = new Coupon();
        if(id != null){
            coupon = couponDao.getCouponById(id);
        }
        if(request.getName()!=null && !request.getName().isEmpty()) coupon.setName(request.getName());
        else{
            if (id == null) throw new FieldEmptyOrNullException("name");
        }
        if(request.getCode()!=null && !request.getCode().isEmpty()) coupon.setCode(request.getCode());
        else{
            if (id == null) throw new FieldEmptyOrNullException("code");
        }
        if(request.getType()!=null) coupon.setType(request.getType());
        else{
            throw new FieldEmptyOrNullException("type");
        }
        if(request.getStartDate()!=null) coupon.setStartDate(request.getStartDate());
        else{
            if (id == null) throw new FieldEmptyOrNullException("startDate");
        }
        if(request.getEndDate()!=null) coupon.setEndDate(request.getEndDate());
        else {
            if (id == null) throw new FieldEmptyOrNullException("endDate");
        }

        if (id ==null && request.getDetails()==null){
            throw new FieldEmptyOrNullException("details");
        }

        if (request.getType() == CouponType.PRODUCT_WISE) {
            logger.info("CREATE COUPON :: PRODUCT WISE");
            if (request.getDetails()!=null){
                ProductWiseDetails details = objectMapper.treeToValue(request.getDetails(), ProductWiseDetails.class);

                if(details.getDiscountType()!=null) coupon.setDiscountType(details.getDiscountType());
                else{
                    if (id == null) throw new FieldEmptyOrNullException("details::discountType");
                }
                if(details.getDiscountAmount()!=null) coupon.setDiscountAmount(details.getDiscountAmount());
                else{
                    if (id == null) throw new FieldEmptyOrNullException("details::discountAmount");
                }
                if(details.getProducts()!=null && !details.getProducts().isEmpty()){
                    List<Product> products = productDao.findAllById(details.getProducts());
                    coupon.setProducts(products);
                }
                else{
                    if (id == null) throw new FieldEmptyOrNullException("details::products");
                }
            }else{
                if (id == null) throw new FieldEmptyOrNullException("details");
            }
        }else if(request.getType() == CouponType.CART_WISE){
            logger.info("CREATE COUPON :: CART WISE");
            if (request.getDetails()!=null){
                CartWiseDetails details = objectMapper.treeToValue(request.getDetails(), CartWiseDetails.class);

                if(details.getThresholdAmount()!=null) coupon.setThresholdAmount(details.getThresholdAmount());
                else{
                    if (id == null) throw new FieldEmptyOrNullException("details::discountType");
                }
                if(details.getDiscountType()!=null) coupon.setDiscountType(details.getDiscountType());
                else{
                    if (id == null) throw new FieldEmptyOrNullException("details::discountType");
                }
                if(details.getDiscountAmount()!=null) coupon.setDiscountAmount(details.getDiscountAmount());
                else{
                    if (id == null) throw new FieldEmptyOrNullException("details::discountAmount");
                }
            }else{
                if (id == null) throw new FieldEmptyOrNullException("details");
            }
        }else if(request.getType() == CouponType.BXGY) {
            logger.info("CREATE COUPON :: BXGY WISE");
            if (request.getDetails()!=null){
                BxGyDetails details = objectMapper.treeToValue(request.getDetails(), BxGyDetails.class);

                if(details.getXProducts()!=null && !details.getXProducts().isEmpty()){
                    coupon.getXProducts().clear();
                    for (CommonProductXQuantityDto dto : details.getXProducts()) {
                        Product product = productDao.findById(dto.getProductId());

                        CouponXProduct cx = new CouponXProduct();
                        cx.setCoupon(coupon);
                        cx.setProduct(product);
                        cx.setQuantityRequired(dto.getQuantity());

                        coupon.getXProducts().add(cx);
                    }
                }
                else{
                    if (id == null) throw new FieldEmptyOrNullException("details::xProducts");
                }

                if(details.getYProducts()!=null && !details.getYProducts().isEmpty()){
                    coupon.getYProducts().clear();
                    for (CommonProductXQuantityDto dto : details.getYProducts()) {
                        Product product = productDao.findById(dto.getProductId());

                        CouponYProduct cy = new CouponYProduct();
                        cy.setCoupon(coupon);
                        cy.setProduct(product);
                        cy.setQuantityRewarded(dto.getQuantity());

                        coupon.getYProducts().add(cy);
                    }
                }
                else{
                    if (id == null) throw new FieldEmptyOrNullException("details::yProducts");
                }
            }else{
                if (id == null) throw new FieldEmptyOrNullException("details");
            }
        }

        return couponDao.createNewCoupon(coupon);
    }


}
