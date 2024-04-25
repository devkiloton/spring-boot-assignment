package com.grocery.payaut.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDiscountSlabDTO {
    private Long discountSlabId;
    private int unitsToGetDiscount;
    private boolean isConstantSlab;
    private int discountAmount;
}
