package com.grocery.payaut.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.grocery.payaut.dto.DiscountCreationDTO;
import com.grocery.payaut.dto.DiscountDTO;
import com.grocery.payaut.dto.DiscountSlabDTO;
import com.grocery.payaut.dto.ItemCreationDTO;
import com.grocery.payaut.dto.ItemDTO;
import com.grocery.payaut.model.Discount;
import com.grocery.payaut.model.DiscountSlab;
import com.grocery.payaut.model.Item;

@Mapper(componentModel = "spring")
public interface ICustomMappers {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void dtoToDiscountSlab(DiscountSlabDTO input, @MappingTarget DiscountSlab output);

    void dtoToDiscountCreation(DiscountCreationDTO input, @MappingTarget Discount output);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void dtoToDiscount(DiscountDTO input, @MappingTarget Discount output);

    void dtoToItemCreation(ItemCreationDTO input, @MappingTarget Item output);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void dtoToItem(ItemDTO input, @MappingTarget Item output);

}
