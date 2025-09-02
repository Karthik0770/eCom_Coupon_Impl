package com.monk.backend.controller;

import com.monk.backend.dto.Product.AddProductRequestDto;
import com.monk.backend.entity.Product;
import com.monk.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("add")
    public ResponseEntity<Product> addProduct(@RequestBody AddProductRequestDto productReq){
        Product savedProduct = productService.createNewProductEntry(productReq);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }
}
