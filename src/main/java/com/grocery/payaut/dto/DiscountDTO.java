package com.grocery.payaut.dto;

import com.grocery.payaut.enumerator.DiscountUnits;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiscountDTO {
    @NotNull
    private Long discountId;
    private DiscountUnits discountUnit;
    private Boolean isConstantSlab;
}
