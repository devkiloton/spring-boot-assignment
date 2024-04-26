package com.grocery.payaut.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.grocery.payaut.dto.CartDTO;
import com.grocery.payaut.dto.CheckoutDTO;
import com.grocery.payaut.dto.ReceiptDTO;
import com.grocery.payaut.model.Cart;
import com.grocery.payaut.model.CartItem;
import com.grocery.payaut.model.Discount;
import com.grocery.payaut.model.DiscountSlab;
import com.grocery.payaut.model.Item;
import com.grocery.payaut.repository.ICartRepository;
import com.grocery.payaut.repository.IItemRepository;
import com.grocery.payaut.util.ItemUtils;

@Service
public class CartService {

    @Autowired
    private ICartRepository cartRepository;

    @Autowired
    private IItemRepository itemRepository;

    public ResponseEntity<List<Cart>> getAllCarts() {
        return ResponseEntity.ok(cartRepository.findAll());
    }

    public ResponseEntity<List<CheckoutDTO>> postCheckoutCart(CartDTO cartDTO) {
        final List<CheckoutDTO> cartItems = cartDTO.getCartItems().stream().map(cartItem -> {
            final Long itemId = cartItem.getItemId();
            final Item item = itemRepository.findById(itemId).get();
            final Discount discount = item.getDiscount();
            final int quantity = cartItem.getQuantity();
            final double price = item.getPrice();
            cartItem.setTotalPrice(price * quantity);
            cartItem.setTotalDiscount(0);

            if (discount == null) {
                final CheckoutDTO checkoutDTO = new CheckoutDTO(item, cartItem);
                return checkoutDTO;
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
                    final CheckoutDTO checkoutDTO = new CheckoutDTO(item, cartItem);
                    return checkoutDTO;
            }
        }).toList();
        return ResponseEntity.ok(cartItems);
    }

    public ResponseEntity<List<ReceiptDTO>> postReceiptCreation(CartDTO cartDTO) {
        final List<ReceiptDTO> receiptData = postCheckoutCart(cartDTO).getBody().stream().map(checkoutDTO -> {
            final ReceiptDTO receiptDTO = new ReceiptDTO();
            receiptDTO.setUnit(checkoutDTO.getItem().getUnit());
            receiptDTO.setQuantity(checkoutDTO.getQuantity());
            receiptDTO.setFinalPrice(checkoutDTO.getTotalPrice() - checkoutDTO.getTotalDiscount());
            receiptDTO.setBasePrice(checkoutDTO.getItem().getPrice());
            receiptDTO.setBaseQuantity((int) checkoutDTO.getItem().getPrice());
            receiptDTO.setItemName(ItemUtils.resolveItemName(checkoutDTO.getItem()));
            return receiptDTO;
        }).toList();

        return ResponseEntity.ok(receiptData);
    }

    private CheckoutDTO applyBreadRule(CartItem cartItem, Item item, double price, int quantity) {
        final long breadAge = ItemUtils.resolveBreadAge(item);

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
        final CheckoutDTO checkoutDTO = new CheckoutDTO(item, cartItem);
        return checkoutDTO;
    }

    private CheckoutDTO applyItemDiscountRule(CartItem cartItem, Item item, double price, int quantity,
            DiscountSlab discountSlab) {
        final double finalValue = quantity * price
                - (quantity * price * discountSlab.getDiscountAmount() / 100);
        cartItem.setTotalPrice(quantity * price);
        cartItem.setTotalDiscount((price * quantity) - finalValue);
        final CheckoutDTO checkoutDTO = new CheckoutDTO(item, cartItem);
        return checkoutDTO;
    }

    private CheckoutDTO applyConstantSlab(CartItem cartItem, Item item, double price, int quantity,
            DiscountSlab discountSlab) {
        final double finalDiscount = (int) Math.floor(quantity /
                discountSlab.getUnitsToGetDiscount())
                * discountSlab.getDiscountAmount();
        cartItem.setTotalPrice(price * quantity);
        cartItem.setTotalDiscount(finalDiscount);
        final CheckoutDTO checkoutDTO = new CheckoutDTO(item, cartItem);
        return checkoutDTO;
    }

}
