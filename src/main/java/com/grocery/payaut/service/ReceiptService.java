package com.grocery.payaut.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.grocery.payaut.dto.ReceiptCreationDTO;
import com.grocery.payaut.dto.CheckoutDTO;
import com.grocery.payaut.dto.ReceiptItemDTO;
import com.grocery.payaut.model.Discount;
import com.grocery.payaut.model.DiscountSlab;
import com.grocery.payaut.model.Item;
import com.grocery.payaut.repository.IItemRepository;
import com.grocery.payaut.util.ItemUtils;

@Service
public class ReceiptService {

    @Autowired
    private IItemRepository itemRepository;

    public ResponseEntity<List<ReceiptItemDTO>> postReceiptCreation(ReceiptCreationDTO receiptCreationDTO) {
        final List<CheckoutDTO> listCheckout = checkoutReceipt(receiptCreationDTO).getBody();
        if (listCheckout != null) {
            final List<ReceiptItemDTO> receiptData = listCheckout.stream()
                    .map(checkoutDTO -> {
                        final ReceiptItemDTO receiptDTO = new ReceiptItemDTO(checkoutDTO);
                        return receiptDTO;
                    }).toList();
            return ResponseEntity.ok(receiptData);
        }
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while creating the receipt");
    }

    private ResponseEntity<List<CheckoutDTO>> checkoutReceipt(ReceiptCreationDTO receiptCreationDTO) {
        final List<CheckoutDTO> cartItems = receiptCreationDTO.getReceiptItems().stream()
                .map(receiptCreationItemDTO -> {
                    final Item possibleItem = this.itemRepository.findById(receiptCreationItemDTO.getItemId()).get();
                    final Discount discount = possibleItem.getDiscount();
                    final double price = possibleItem.getPrice();
                    final int quantity = receiptCreationItemDTO.getQuantity();
                    final ReceiptItemDTO receiptItemDTO = new ReceiptItemDTO();

                    receiptItemDTO.setQuantity(quantity);
                    receiptItemDTO.setTotalPrice(price * quantity);
                    receiptItemDTO.setTotalDiscount(0.0);
                    if (discount == null) {
                        final CheckoutDTO checkoutDTO = new CheckoutDTO(possibleItem, receiptItemDTO);
                        return checkoutDTO;
                    }

                    final DiscountSlab discountSlab = discount.getDiscountSlabs().stream()
                            .filter(slab -> slab.getUnitsToGetDiscount() <= quantity).reduce((first, second) -> second)
                            .orElse(null);

                    if (discount.getIsConstantSlab()) {
                        return applyConstantSlab(receiptItemDTO, possibleItem, price, quantity, discountSlab);
                    }

                    switch (possibleItem.getType()) {
                        case BREADS:

                            return applyBreadRule(receiptItemDTO, possibleItem, price, quantity);
                        case VEGETABLES:

                            return applyVegetableRule(receiptItemDTO, possibleItem, price, quantity, discountSlab);
                        default:

                            final CheckoutDTO checkoutDTO = new CheckoutDTO(possibleItem, receiptItemDTO);
                            return checkoutDTO;
                    }
                }).toList();
        return ResponseEntity.ok(cartItems);
    }

    private CheckoutDTO applyBreadRule(ReceiptItemDTO receiptItemDTO, Item item, double price, int quantity) {
        final long breadAge = ItemUtils.resolveBreadAge(item);
        int quantityToGive = 0;

        if (breadAge > 6) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Bread is too old to be sold! 🤮");
        }
        if (breadAge >= 3) {
            quantityToGive = 1;
        }
        if (breadAge == 6) {
            quantityToGive = 2;
        }
        final int finalQuantity = quantity + quantityToGive;
        receiptItemDTO.setQuantity(finalQuantity);
        receiptItemDTO.setTotalDiscount(price * quantityToGive);
        receiptItemDTO.setTotalPrice(price * finalQuantity);
        final CheckoutDTO checkoutDTO = new CheckoutDTO(item, receiptItemDTO);
        return checkoutDTO;
    }

    private CheckoutDTO applyVegetableRule(ReceiptItemDTO cartItem, Item item, double price, int quantity,
            DiscountSlab discountSlab) {
        final double finalPrice = quantity * price;
        final double discountPercent = discountSlab.getDiscountAmount() / 100;
        final double finalPriceWithDiscount = finalPrice - (finalPrice * discountPercent);

        cartItem.setTotalPrice(finalPrice);
        cartItem.setTotalDiscount(finalPrice - finalPriceWithDiscount);
        final CheckoutDTO checkoutDTO = new CheckoutDTO(item, cartItem);
        return checkoutDTO;
    }

    private CheckoutDTO applyConstantSlab(ReceiptItemDTO cartItem, Item item, double price, int quantity,
            DiscountSlab discountSlab) {

        final double finalDiscount = Math.floor(quantity /
                discountSlab.getUnitsToGetDiscount())
                * discountSlab.getDiscountAmount();
        cartItem.setTotalPrice(price * quantity);
        cartItem.setTotalDiscount(finalDiscount);
        final CheckoutDTO checkoutDTO = new CheckoutDTO(item, cartItem);
        return checkoutDTO;
    }

}
