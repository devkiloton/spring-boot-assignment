package com.grocery.payaut.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.grocery.payaut.dto.CartDTO;
import com.grocery.payaut.model.Cart;
import com.grocery.payaut.model.CartItem;
import com.grocery.payaut.model.Discount;
import com.grocery.payaut.model.DiscountSlab;
import com.grocery.payaut.model.Item;
import com.grocery.payaut.repository.ICartRepository;
import com.grocery.payaut.repository.IItemRepository;

@Service
public class CartService {

    @Autowired
    private ICartRepository cartRepository;

    @Autowired
    private IItemRepository itemRepository;

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    public ResponseEntity<List<CartItem>> postCheckoutCart(CartDTO cartDTO) {
        final List<CartItem> cartItems = cartDTO.getCartItems().stream().map(cartItem -> {
            final Long itemId = cartItem.getCartItemId();
            final Item item = itemRepository.findById(itemId).get();
            final Discount discount = item.getDiscount();
            final int quantity = cartItem.getQuantity();
            final double price = item.getPrice();
            cartItem.setTotalPrice(price * quantity);
            cartItem.setTotalDiscount(0);

            if (discount == null) {
                return cartItem;
            }

            final DiscountSlab discountSlab = discount.getDiscountSlabs().stream()
                    .filter(slab -> slab.getUnitsToGetDiscount() <= quantity).findFirst().orElse(null);

            if (discount.getIsConstantSlab()) {
                return applyConstantSlab(cartItem, item, price, quantity, discountSlab);
            }
            switch (item.getType()) {
                case BREADS:
                    return applyBreadRule(cartItem, item, price, quantity);
                case VEGETABLES:
                    return applyItemDiscountRule(cartItem, item, price, quantity, discountSlab);
                default:
                    return cartItem;
            }
        }).toList();
        return ResponseEntity.ok(cartItems);
    }

    private CartItem applyBreadRule(CartItem cartItem, Item item, double price, int quantity) {
        final long breadAge = item.getCreatedAt().until(LocalDateTime.now(), ChronoUnit.DAYS);

        if (breadAge > 6) {
            throw new IllegalArgumentException("Bread is expired");
        }
        if (breadAge >= 3) {
            cartItem.setQuantity(quantity + 1);
            cartItem.setTotalDiscount(price * 1);
            cartItem.setTotalPrice(price * (quantity + 1));
        }
        if (breadAge == 6) {
            cartItem.setQuantity(quantity + 2);
            cartItem.setTotalDiscount(price * 2);
            cartItem.setTotalPrice(price * (quantity + 2));
        }
        return cartItem;
    }

    private CartItem applyItemDiscountRule(CartItem cartItem, Item item, double price, int quantity,
            DiscountSlab discountSlab) {
        final double finalValue = quantity * price
                - (quantity * price * discountSlab.getDiscountAmount() / 100);
        cartItem.setTotalPrice(quantity * price);
        cartItem.setTotalDiscount((price * quantity) - finalValue);
        return cartItem;
    }

    private CartItem applyConstantSlab(CartItem cartItem, Item item, double price, int quantity,
            DiscountSlab discountSlab) {
        // split that in finals later
        final double finalDiscount = (int) Math.floor(quantity /
                discountSlab.getUnitsToGetDiscount())
                * discountSlab.getDiscountAmount();
        cartItem.setTotalPrice(price * quantity);
        cartItem.setTotalDiscount(finalDiscount);
        return cartItem;
    }

}
