package com.grocery.payaut.dto;

import java.util.List;

import org.springframework.validation.annotation.Validated;

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
public class DiscountCreationDTO {
    @NotNull
    private Long itemId;
    @NotNull

    private DiscountUnits discountUnit;
    @NotNull

    private Boolean isConstantSlab;
    @NotNull

    private List<DiscountSlabDTO> discountSlabs;
}
