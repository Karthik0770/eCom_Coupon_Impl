package com.monk.backend.controller;

import com.monk.backend.dto.Product.AddProductRequestDto;
import com.monk.backend.dto.ResponseDto;
import com.monk.backend.entity.Product;
import com.monk.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("add")
    public ResponseEntity<ResponseDto<Product>> addProduct(@RequestBody AddProductRequestDto productReq){
        ResponseDto<Product> response = new ResponseDto<>();

        try{
            Product savedProduct = productService.createNewProductEntry(productReq);
            response.setPayload(savedProduct);
        }catch (Exception e){
            response.setStatus("FAILURE");
            response.setMessage(e.getMessage());
            response.setPayload(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.setStatus("SUCCESS");
        response.setMessage("Product created successfully!");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("get")
    public ResponseEntity<ResponseDto<List<Product>>> fetchProducts(){
        ResponseDto<List<Product>> response = new ResponseDto<>();

        try{
            List<Product> allProducts = productService.getAllProducts();
            response.setPayload(allProducts);
        }catch (Exception e){
            response.setStatus("FAILURE");
            response.setMessage(e.getMessage());
            response.setPayload(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.setStatus("SUCCESS");
        response.setMessage("Products fetched successfully!");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
