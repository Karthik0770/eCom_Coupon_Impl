package com.monk.backend.dao;

import com.monk.backend.entity.Cart;
import com.monk.backend.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CartDao {

    @Autowired
    CartRepository cartRepository;

    public Cart createNewCart(Cart cart){
        return cartRepository.save(cart);
    }
}
