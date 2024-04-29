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
public class ItemDTO {
    @NotNull
    private Long id;
    private ItemTypes type;
    private String name;
    private Double price;
    private MeasurementUnits unit;
    private Discount discount;
}
