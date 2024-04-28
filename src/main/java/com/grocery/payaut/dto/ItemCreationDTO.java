package com.grocery.payaut.dto;

import com.grocery.payaut.enumerator.ItemTypes;
import com.grocery.payaut.enumerator.MeasurementUnits;
import com.grocery.payaut.model.Discount;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemCreationDTO {
    @NotNull
    private ItemTypes type;
    @NotNull
    private Double price;
    @NotNull
    private MeasurementUnits unit;
    @NotNull
    private String name;
    private Discount discount;
}
