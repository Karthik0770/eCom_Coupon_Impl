package com.monk.backend.service;

import com.monk.backend.entity.Cart;
import org.springframework.stereotype.Service;

@Service
public interface CartService {
    public Cart initialiseNewCart();
}
