package com.monk.backend.dao;

import com.monk.backend.entity.Product;
import com.monk.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProductDao {

    @Autowired
    ProductRepository productRepository;

    public List<Product> findAllById(List<Integer> productIds){
        return productRepository.findAllById(productIds);
    }

    public Product findById(int id){
        Optional<Product> product = productRepository.findById(id);
        return product.orElse(null);
    }
}
