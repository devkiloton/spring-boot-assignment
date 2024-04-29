package com.grocery.payaut.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiscountSlabDTO {
    private Integer unitsToGetDiscount;
    private Double discountAmount;
}
