package com.monk.backend.service;

import com.monk.backend.dto.Product.AddProductRequestDto;
import com.monk.backend.entity.Product;

public interface ProductService {
    public Product createNewProductEntry(AddProductRequestDto productReq);
}
