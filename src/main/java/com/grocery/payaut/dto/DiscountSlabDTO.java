package com.grocery.payaut.dto;

import org.springframework.validation.annotation.Validated;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class DiscountSlabDTO {
    private Integer unitsToGetDiscount;
    private Double discountAmount;
}
