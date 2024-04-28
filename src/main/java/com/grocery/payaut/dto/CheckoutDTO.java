package com.grocery.payaut.dto;

import com.grocery.payaut.model.Item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutDTO extends ReceiptItemDTO {
    Item item;
    Double totalDiscount;

    public CheckoutDTO(Item item, ReceiptItemDTO receiptItemDTO) {
        this.item = item;
        this.setQuantity(receiptItemDTO.getQuantity());
        this.setBasePrice(receiptItemDTO.getBasePrice());
        this.setFinalPrice(receiptItemDTO.getFinalPrice());
        this.setUnit(receiptItemDTO.getUnit());
        this.setItemName(receiptItemDTO.getItemName());
        this.totalDiscount = receiptItemDTO.getTotalDiscount();
        this.setTotalPrice(receiptItemDTO.getTotalPrice());
    }
}