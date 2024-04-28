package com.grocery.payaut.dto;

import com.grocery.payaut.enumerator.MeasurementUnits;

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
}
