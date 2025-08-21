package com.monk.backend.controller;

import com.monk.backend.dto.Cart.InitCartResponseDto;
import com.monk.backend.entity.Cart;
import com.monk.backend.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.html.HTMLTableCaptionElement;

@RestController
@RequestMapping("cart")
public class CartController {

    @Autowired
    CartService cartService;

    public ResponseEntity<InitCartResponseDto> initCart(){
        Cart cart = cartService.initialiseNewCart();
        return new ResponseEntity<>(
                new InitCartResponseDto(HttpStatus.OK, cart.getCartId(),"Cart initialised"),
                HttpStatus.OK
        );
    }
}
