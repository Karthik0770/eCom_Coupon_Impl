package com.monk.backend.serviceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.monk.backend.dao.CouponDao;
import com.monk.backend.dao.ProductDao;
import com.monk.backend.dto.Cart.CartRequestDto;
import com.monk.backend.dto.Coupon.*;
import com.monk.backend.entity.Coupon;
import com.monk.backend.entity.CouponXProduct;
import com.monk.backend.entity.CouponYProduct;
import com.monk.backend.entity.Product;
import com.monk.backend.exceptions.CouponNotFoundException;
import com.monk.backend.exceptions.FieldEmptyOrNullException;
import com.monk.backend.exceptions.ProductNotFoundException;
import com.monk.backend.service.CouponService;
import com.monk.backend.utils.CouponType;
import com.monk.backend.utils.DiscountType;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
        List<Coupon> coupons = couponDao.getCoupons();
        if (coupons==null) throw new CouponNotFoundException(-1);
        return coupons;
    }

    @Override
    public Coupon getCouponById(int id) {
        Coupon coupon = couponDao.getCouponById(id);
        if (coupon==null) throw new CouponNotFoundException(id);
        return coupon;
    }

    @Override
    public Coupon deleteCouponByID(int id) {
        Coupon deletedCoupon = couponDao.deleteCouponById(id);
        if (deletedCoupon == null) throw new CouponNotFoundException(id);
        return deletedCoupon;
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
                    if (products==null) throw new ProductNotFoundException(-1);
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
                        if (product == null) throw new ProductNotFoundException(dto.getProductId());
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
                        if (product == null) throw new ProductNotFoundException(dto.getProductId());
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
                if(details.getRepetitionLimit()!=null) coupon.setRepetitionLimit(details.getRepetitionLimit());
                else{
                    if (id == null) throw new FieldEmptyOrNullException("details::repetitionLimit");
                }
            }else{
                if (id == null) throw new FieldEmptyOrNullException("details");
            }
        }

        return couponDao.createNewCoupon(coupon);
    }

    @Override
    public List<DiscountObjectDto> getApplicableCouponsForCart(CartRequestDto cart) {
        List<Coupon> allCoupons = couponDao.getCoupons();
        List<DiscountObjectDto> applicableCoupons = new ArrayList<>();
        for(Coupon c: allCoupons){
            if(c.getType()==CouponType.CART_WISE && CartWiseDetails.isApplicable(cart,c,productDao)){
                applicableCoupons.add(calculateDiscountForCart(cart,c));
            }else if(c.getType()==CouponType.PRODUCT_WISE && ProductWiseDetails.isApplicable(cart,c)){
                applicableCoupons.add(calculateDiscountForCart(cart,c));
            }else if(c.getType()==CouponType.BXGY && BxGyDetails.isApplicable(cart,c)){
                applicableCoupons.add(calculateDiscountForCart(cart,c));
            }
        }

        applicableCoupons.sort((a, b) -> Integer.compare(b.getDiscountAmount(), a.getDiscountAmount()));

        return applicableCoupons;
    }

    @Override
    public DiscountObjectDto applyCouponOnCart(CartRequestDto cart, Integer id) {
        Coupon couponToBeApplied = couponDao.getCouponById(id);
        if(couponToBeApplied.getType()==CouponType.CART_WISE && CartWiseDetails.isApplicable(cart,couponToBeApplied,productDao)){
            return calculateDiscountForCart(cart,couponToBeApplied);
        }else if(couponToBeApplied.getType()==CouponType.PRODUCT_WISE && ProductWiseDetails.isApplicable(cart,couponToBeApplied)){
            return calculateDiscountForCart(cart,couponToBeApplied);
        }else if(couponToBeApplied.getType()==CouponType.BXGY && BxGyDetails.isApplicable(cart,couponToBeApplied)){
            return calculateDiscountForCart(cart,couponToBeApplied);
        }
        return null;
    }

    public DiscountObjectDto calculateDiscountForCart(CartRequestDto cart, Coupon coupon) {
        DiscountObjectDto discountObject = new DiscountObjectDto();

        List<Integer> productIds = cart.getCartProducts().stream()
                .map(CommonProductXQuantityDto::getProductId)
                .collect(Collectors.toList());

        List<Product> productList = new ArrayList<>();
        for(Integer pId:productIds){
            Product p = productDao.findById(pId);
            if(p==null) throw new ProductNotFoundException(pId);
            productList.add(p);
        }

        Map<Integer, Product> productMap = productList.stream()
                .collect(Collectors.toMap(Product::getProductId, product -> product));

        int cartTotal = cart.getCartProducts().stream()
                .mapToInt(item -> {
                    Product product = productMap.get(item.getProductId());
                    return product.getPrice() * item.getQuantity();
                })
                .sum();

        discountObject.setType(coupon.getType());
        discountObject.setCode(coupon.getCode());

        switch (coupon.getType()) {
            case CART_WISE:
                return calculateCartWiseDiscount(cart, coupon, cartTotal, discountObject);
            case PRODUCT_WISE:
                return calculateProductWiseDiscount(cart, coupon, cartTotal, productMap, discountObject);
            case BXGY:
                return calculateBxGyDiscount(cart, coupon, cartTotal, productMap, discountObject);
            default:
                throw new IllegalArgumentException("Unsupported coupon type: " + coupon.getType());
        }
    }

    private DiscountObjectDto calculateCartWiseDiscount(CartRequestDto cart, Coupon coupon,
                                                        int cartTotal, DiscountObjectDto discountObject) {
        if (cartTotal < coupon.getThresholdAmount()) {
            discountObject.setDiscountAmount(0);
            discountObject.setFinalCartAmount(cartTotal);
            discountObject.setMessage("Cart total must be at least ₹" + coupon.getThresholdAmount());
            return discountObject;
        }

        int discountAmount;
        if (coupon.getDiscountType() == DiscountType.FLAT) {
            discountAmount = Math.min(coupon.getDiscountAmount(), cartTotal); // Ensure discount doesn't exceed cart total
        } else if (coupon.getDiscountType() == DiscountType.PERCENTAGE) {
            discountAmount = (cartTotal * coupon.getDiscountAmount()) / 100;
        } else {
            throw new IllegalArgumentException("Unsupported discount type: " + coupon.getDiscountType());
        }
        discountObject.setOriginalCartAmount(cartTotal);
        discountObject.setCouponId(coupon.getCouponId());
        discountObject.setDiscountAmount(discountAmount);
        discountObject.setFinalCartAmount(cartTotal - discountAmount);
        discountObject.setMessage("Get " + discountAmount + " discount on your cart!");

        return discountObject;
    }

    private DiscountObjectDto calculateProductWiseDiscount(CartRequestDto cart, Coupon coupon,
                                                           int cartTotal, Map<Integer, Product> productMap,
                                                           DiscountObjectDto discountObject) {
        int discountAmount = 0;
        Set<Integer> applicableProductIds = coupon.getProducts().stream()
                .map(Product::getProductId)
                .collect(Collectors.toSet());

        for (CommonProductXQuantityDto item : cart.getCartProducts()) {
            if (applicableProductIds.contains(item.getProductId())) {
                Product product = productMap.get(item.getProductId());
                int productTotal = product.getPrice() * item.getQuantity();

                if (coupon.getDiscountType() == DiscountType.FLAT) {
                    discountAmount += Math.min(coupon.getDiscountAmount() * item.getQuantity(), productTotal);
                } else if (coupon.getDiscountType() == DiscountType.PERCENTAGE) {
                    discountAmount += (productTotal * coupon.getDiscountAmount()) / 100;
                }
            }
        }
        discountObject.setOriginalCartAmount(cartTotal);
        discountObject.setCouponId(coupon.getCouponId());
        discountObject.setDiscountAmount(discountAmount);
        discountObject.setFinalCartAmount(cartTotal - discountAmount);
        discountObject.setMessage("Get " + discountAmount + " discount on selected products!");

        return discountObject;
    }

    private DiscountObjectDto calculateBxGyDiscount(CartRequestDto cart, Coupon coupon,
                                                    Integer cartTotal, Map<Integer, Product> productMap,
                                                    DiscountObjectDto discountObject) {

        List<CouponXProduct> buyRequirements = coupon.getXProducts();
        List<CouponYProduct> getRewards = coupon.getYProducts();

        Map<Integer, Integer> cartProductQuantities = new HashMap<>();
        for (CommonProductXQuantityDto item : cart.getCartProducts()) {
            cartProductQuantities.put(item.getProductId(), item.getQuantity());
        }

        int maxPossibleSets = calculateMaxBuySets(buyRequirements, cartProductQuantities);

        if (maxPossibleSets == 0) {
            discountObject.setDiscountAmount(0);
            discountObject.setFinalCartAmount(cartTotal);
            discountObject.setMessage("BxGy offer not applicable - insufficient buy quantities");
            return discountObject;
        }

        int maxRewardSetsAvailable = calculateMaxRewardSets(getRewards, cartProductQuantities);

        int applicableSets = Math.min(maxPossibleSets,
                Math.min(maxRewardSetsAvailable, coupon.getRepetitionLimit()));

        if (applicableSets == 0) {
            discountObject.setDiscountAmount(0);
            discountObject.setFinalCartAmount(cartTotal);
            discountObject.setMessage("BxGy offer not applicable - insufficient reward items in cart");
            return discountObject;
        }

        int totalDiscount = calculateRewardDiscount(getRewards, applicableSets, productMap);

        discountObject.setOriginalCartAmount(cartTotal);
        discountObject.setCouponId(coupon.getCouponId());
        discountObject.setDiscountAmount(totalDiscount);
        discountObject.setFinalCartAmount(cartTotal - totalDiscount);
        discountObject.setMessage(String.format("BxGy offer applied %d time(s). You save ₹%d!",
                applicableSets, totalDiscount));

        return discountObject;
    }

    private int calculateMaxBuySets(List<CouponXProduct> buyRequirements,
                                    Map<Integer, Integer> cartProductQuantities) {

        int maxSets = Integer.MAX_VALUE;

        for (CouponXProduct requirement : buyRequirements) {
            int productId = requirement.getProduct().getProductId();
            int requiredQuantity = requirement.getQuantityRequired();
            int availableQuantity = cartProductQuantities.getOrDefault(productId, 0);

            int possibleSetsForThisProduct = availableQuantity / requiredQuantity;

            maxSets = Math.min(maxSets, possibleSetsForThisProduct);
        }

        return maxSets == Integer.MAX_VALUE ? 0 : maxSets;
    }

    private int calculateMaxRewardSets(List<CouponYProduct> getRewards,
                                       Map<Integer, Integer> cartProductQuantities) {

        int maxRewardSets = Integer.MAX_VALUE;

        for (CouponYProduct reward : getRewards) {
            int productId = reward.getProduct().getProductId();
            int rewardQuantity = reward.getQuantityRewarded();
            int availableQuantity = cartProductQuantities.getOrDefault(productId, 0);

            int possibleRewardSetsForThisProduct = availableQuantity / rewardQuantity;

            maxRewardSets = Math.min(maxRewardSets, possibleRewardSetsForThisProduct);
        }

        return maxRewardSets == Integer.MAX_VALUE ? 0 : maxRewardSets;
    }

    private int calculateRewardDiscount(List<CouponYProduct> getRewards, int applicableSets,
                                        Map<Integer, Product> productMap) {

        int totalDiscount = 0;

        for (CouponYProduct reward : getRewards) {
            int productId = reward.getProduct().getProductId();
            int rewardQuantityPerSet = reward.getQuantityRewarded();

            Product product = productMap.get(productId);
            if (product != null) {
                int totalFreeQuantity = rewardQuantityPerSet * applicableSets;
                int discountForThisProduct = product.getPrice() * totalFreeQuantity;
                totalDiscount += discountForThisProduct;
            }
        }

        return totalDiscount;
    }
}
