package com.grocery.payaut.dto;

import java.util.List;

import com.grocery.payaut.enumerator.DiscountUnits;
import com.grocery.payaut.model.DiscountSlab;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiscountCreationDTO {
    @NotNull
    private Long id;

    @NotNull
    private DiscountUnits discountUnit;

    @NotNull
    private Boolean isConstantSlab;

    @NotNull
    private List<DiscountSlabDTO> discountSlabs;
}
