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
import com.grocery.payaut.util.MathUtils;

@Service
public class ReceiptService {

    @Autowired
    private IItemRepository itemRepository;

    public ResponseEntity<List<ReceiptItemDTO>> postReceiptCreation(ReceiptCreationDTO receiptCreationDTO) {
        final List<ReceiptItemDTO> receiptData = checkoutCart(receiptCreationDTO).getBody().stream()
                .map(checkoutDTO -> {
                    final ReceiptItemDTO receiptDTO = new ReceiptItemDTO();
                    receiptDTO.setUnit(checkoutDTO.getItem().getUnit());
                    receiptDTO.setQuantity(checkoutDTO.getQuantity());
                    final double finalPrice = MathUtils
                            .roundTwoDecimals(checkoutDTO.getTotalPrice() - checkoutDTO.getTotalDiscount());
                    receiptDTO
                            .setFinalPrice(finalPrice);
                    receiptDTO.setBasePrice(checkoutDTO.getItem().getPrice());
                    receiptDTO.setItemName(ItemUtils.resolveItemName(checkoutDTO.getItem()));
                    receiptDTO.setTotalPrice(checkoutDTO.getTotalPrice());
                    final double totalDiscount = MathUtils.roundTwoDecimals(checkoutDTO.getTotalDiscount());
                    receiptDTO.setTotalDiscount(totalDiscount);
                    return receiptDTO;
                }).toList();

        return ResponseEntity.ok(receiptData);
    }

    // TODO: make it private and test it with the postReceiptCreation method
    public ResponseEntity<List<CheckoutDTO>> checkoutCart(ReceiptCreationDTO receiptCreationDTO) {
        final List<CheckoutDTO> cartItems = receiptCreationDTO.getReceiptItems().stream()
                .map(receiptCreationItemDTO -> {
                    final Long itemId = receiptCreationItemDTO.getItemId();
                    final Item possibleItem = itemRepository.findById(itemId).get();
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

        if (breadAge > 6) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Bread is too old to be sold! 🤮");
        }
        if (breadAge >= 3) {
            receiptItemDTO.setQuantity(quantity + 1);
            receiptItemDTO.setTotalDiscount(price * 1);
            receiptItemDTO.setTotalPrice(price * (quantity + 1));
        }
        if (breadAge == 6) {
            receiptItemDTO.setQuantity(quantity + 2);
            receiptItemDTO.setTotalDiscount(price * 2);
            receiptItemDTO.setTotalPrice(price * (quantity + 2));
        }
        final CheckoutDTO checkoutDTO = new CheckoutDTO(item, receiptItemDTO);
        return checkoutDTO;
    }

    private CheckoutDTO applyVegetableRule(ReceiptItemDTO cartItem, Item item, double price, int quantity,
            DiscountSlab discountSlab) {
        final double finalValue = quantity * price
                - (quantity * price * discountSlab.getDiscountAmount() / 100);

        cartItem.setTotalPrice(quantity * price);
        cartItem.setTotalDiscount((price * quantity) - finalValue);
        final CheckoutDTO checkoutDTO = new CheckoutDTO(item, cartItem);
        return checkoutDTO;
    }

    private CheckoutDTO applyConstantSlab(ReceiptItemDTO cartItem, Item item, double price, int quantity,
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
