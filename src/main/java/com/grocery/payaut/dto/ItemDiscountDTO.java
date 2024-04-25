package com.grocery.payaut.dto;

import java.util.Set;

import com.grocery.payaut.model.DiscountSlab;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDiscountDTO {
    private Long discountId;
    private String discountUnit;
    private Boolean isDiscountConstant;
    private Set<DiscountSlab> discountSlabs;
}
