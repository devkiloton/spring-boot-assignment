package com.grocery.payaut.dto;

import java.util.List;

import com.grocery.payaut.enumerator.DiscountUnits;
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
    private DiscountUnits discountUnit;
    private Boolean isDiscountConstant;
    private List<DiscountSlab> discountSlabs;
}
