package com.grocery.payaut.dto;

import com.grocery.payaut.model.Discount;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {
    private Long itemId;
    private String type;
    private double price;
    private String unit;
    private Discount discount;
}
