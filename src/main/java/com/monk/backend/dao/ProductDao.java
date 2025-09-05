package com.monk.backend.dao;

import com.monk.backend.entity.Coupon;
import com.monk.backend.entity.Product;
import com.monk.backend.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProductDao {

    @Autowired
    ProductRepository productRepository;
    Logger logger = LoggerFactory.getLogger("logger");

    public List<Product> findAll(){
        List<Product> products = null;
        try {
            products = productRepository.findAll();
        }catch (Exception e){
            logger.error("Error while fetching products from db");
        }
        if (products!=null && products.isEmpty()) return null;
        return products;
    }

    public List<Product> findAllById(List<Integer> productIds){
        List<Product> products = null;
        try{
            products =productRepository.findAllById(productIds);
        }catch (Exception e){
            logger.error("Error while fetching products from db");
        }
        if (products!=null && products.isEmpty()) return null;
        return products;
    }

    public Product findById(int id){
        Optional<Product> product = productRepository.findById(id);
        return product.orElse(null);
    }

    public Product addNewProduct(Product productToBeSaved){
        return productRepository.save(productToBeSaved);
    }
}
