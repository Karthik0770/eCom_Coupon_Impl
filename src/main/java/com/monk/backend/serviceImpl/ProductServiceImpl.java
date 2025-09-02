package com.monk.backend.serviceImpl;

import com.monk.backend.dao.ProductDao;
import com.monk.backend.dto.Product.AddProductRequestDto;
import com.monk.backend.entity.Product;
import com.monk.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDao productDao;

    @Override
    public Product createNewProductEntry(AddProductRequestDto productReq) {
        Product newProduct = new Product();
        newProduct.setProductName(productReq.getProductName());
        newProduct.setPrice(productReq.getPrice());

        return productDao.addNewProduct(newProduct);
    }
}
