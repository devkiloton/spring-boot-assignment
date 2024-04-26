package com.grocery.payaut.dto;

import com.grocery.payaut.model.CartItem;
import com.grocery.payaut.model.Item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutDTO extends CartItem {
    Item item;

    public CheckoutDTO(Item item, CartItem cartItem) {
        this.item = item;
        this.setQuantity(cartItem.getQuantity());
        this.setTotalDiscount(cartItem.getTotalDiscount());
        this.setTotalPrice(cartItem.getTotalPrice());
    }
}