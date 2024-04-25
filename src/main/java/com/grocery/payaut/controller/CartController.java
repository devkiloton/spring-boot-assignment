package com.grocery.payaut.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grocery.payaut.dto.CartDTO;
import com.grocery.payaut.model.Cart;
import com.grocery.payaut.model.CartItem;
import com.grocery.payaut.service.CartService;

@RestController
// change later to be in the index
@RequestMapping("/api/v1")
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * Get all carts checked out in the database (just to prove that the database
     * has initial data)
     */
    @GetMapping("/carts")
    public List<Cart> getAllOrders() {
        return this.cartService.getAllCarts();
    }

    /**
     * Checkout a cart, apply discount rules and return values with final
     * prices and discounts applied
     */
    @PostMapping("/checkout")
    public ResponseEntity<List<CartItem>> postCheckoutOrder(@RequestBody CartDTO cartDTO) {
        return this.cartService.postCheckoutCart(cartDTO);
    }

}