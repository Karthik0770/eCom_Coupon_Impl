package com.monk.backend.exceptions;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Integer productId) {
        super("Product provided were found - "+productId);
    }
}
