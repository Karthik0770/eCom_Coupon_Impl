package com.monk.backend.controller;

import com.monk.backend.dto.Product.AddProductRequestDto;
import com.monk.backend.entity.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("product")
public class ProductController {
    public ResponseEntity<Product> addProduct(@RequestBody AddProductRequestDto req){

    }
}
