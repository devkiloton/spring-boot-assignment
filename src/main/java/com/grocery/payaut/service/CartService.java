package com.grocery.payaut.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.grocery.payaut.model.Cart;
import com.grocery.payaut.repository.ICartRepository;

@Service
public class CartService {

    @Autowired
    private ICartRepository cartRepository;

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    public ResponseEntity<Cart> postCheckoutOrder(Cart order) {
        return ResponseEntity.ok(order);
    }

    // THESE METHODS SHOULD DEFINITELY NOT BE IN THE CONTROLLER
    public void applyItemDiscountRule() {
        // This method is not implemented
    }

}
