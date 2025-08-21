package com.monk.backend.serviceImpl;

import com.monk.backend.dao.CartDao;
import com.monk.backend.entity.Cart;
import com.monk.backend.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartDao cartDao;

    @Override
    public Cart initialiseNewCart() {
        return cartDao.createNewCart(new Cart());
    }
}
