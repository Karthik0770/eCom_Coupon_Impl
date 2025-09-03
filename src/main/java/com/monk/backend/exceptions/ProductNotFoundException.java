package com.monk.backend.exceptions;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Integer productId) {
        super("None of the products provided were found - "+productId);
    }
}
