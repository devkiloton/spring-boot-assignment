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
import com.grocery.payaut.dto.CheckoutDTO;
import com.grocery.payaut.dto.ReceiptDTO;
import com.grocery.payaut.model.Cart;
import com.grocery.payaut.service.CartService;

@RestController
@RequestMapping("carts")
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * Get all carts checked out in the database (just to prove that the database
     * has initial data)
     */
    @GetMapping("/all")
    public ResponseEntity<List<Cart>> getAllOrders() {
        return this.cartService.getAllCarts();
    }

    /**
     * Checkout a cart, apply discount rules and return necessary details to
     * generate a receipt
     */
    @PostMapping("/checkout")
    public ResponseEntity<List<CheckoutDTO>> postCheckoutOrder(@RequestBody CartDTO cartDTO) {
        return this.cartService.postCheckoutCart(cartDTO);
    }

    /**
     * Generate a receipt for a cart
     */
    @PostMapping("/receipt")
    public ResponseEntity<List<ReceiptDTO>> postReceiptCreation(@RequestBody CartDTO cartDTO) {
        return this.cartService.postReceiptCreation(cartDTO);
    }

}