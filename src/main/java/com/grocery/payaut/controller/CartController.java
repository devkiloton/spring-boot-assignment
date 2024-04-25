package com.grocery.payaut.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grocery.payaut.model.Cart;
import com.grocery.payaut.service.CartService;

@RestController
// change later to be in the index
@RequestMapping("/api/v1")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/carts")
    public List<Cart> getAllOrders() {
        return this.cartService.getAllCarts();
    }

    @PostMapping("/checkout")
    public ResponseEntity<Cart> postCheckoutOrder(@RequestBody Cart order) {
        return this.cartService.postCheckoutOrder(order);
    }

}