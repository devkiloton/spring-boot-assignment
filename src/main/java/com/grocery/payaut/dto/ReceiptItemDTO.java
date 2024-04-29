package com.grocery.payaut.dto;

import com.grocery.payaut.enumerator.MeasurementUnits;
import com.grocery.payaut.util.ItemUtils;
import com.grocery.payaut.util.MathUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptItemDTO {
    private String itemName;
    private Double finalPrice;
    private Integer quantity;
    private MeasurementUnits unit;
    private Double totalPrice;
    private Double totalDiscount;

    // Fields for the user comparison with the final price
    private Double basePrice;

    public ReceiptItemDTO(CheckoutDTO checkoutDTO) {
        this.setUnit(checkoutDTO.getItem().getUnit());
        this.setQuantity(checkoutDTO.getQuantity());
        final double finalPrice = MathUtils
                .roundTwoDecimals(checkoutDTO.getTotalPrice() - checkoutDTO.getTotalDiscount());
        this.setFinalPrice(finalPrice);
        this.setBasePrice(checkoutDTO.getItem().getPrice());
        this.setItemName(ItemUtils.resolveItemName(checkoutDTO.getItem()));
        this.setTotalPrice(checkoutDTO.getTotalPrice());
        final double totalDiscount = MathUtils.roundTwoDecimals(checkoutDTO.getTotalDiscount());
        this.setTotalDiscount(totalDiscount);
    }
}
