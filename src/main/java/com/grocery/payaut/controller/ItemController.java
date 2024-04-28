package com.grocery.payaut.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grocery.payaut.dto.ItemDTO;
import com.grocery.payaut.dto.DiscountCreationDTO;
import com.grocery.payaut.dto.DiscountDTO;
import com.grocery.payaut.dto.DiscountSlabDTO;
import com.grocery.payaut.dto.ItemCreationDTO;
import com.grocery.payaut.model.Discount;
import com.grocery.payaut.model.DiscountSlab;
import com.grocery.payaut.model.Item;
import com.grocery.payaut.service.ItemService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("items")
@Validated
public class ItemController {

    @Autowired
    private ItemService itemService;

    /**
     * Get all items in the database with its discounts
     * 
     * @return {@link List}
     */
    @GetMapping("/all")
    public ResponseEntity<List<Item>> getItems() {
        return this.itemService.getAllItems();
    }

    /**
     * Create an item in the database
     */
    @PostMapping("/create-item")
    public ResponseEntity<Item> createItem(@Valid @RequestBody ItemCreationDTO itemCreationDTO) {
        return this.itemService.createItem(itemCreationDTO);
    }

    /**
     * Update an item in the database
     */
    @PutMapping("/change-item")
    public ResponseEntity<Item> updateItem(@Valid @RequestBody ItemDTO itemDTO) {
        return this.itemService.updateItem(itemDTO);
    }

    /**
     * Add or update an item discount for an item in the database
     */
    @PutMapping("/change-item-discount")
    public ResponseEntity<Discount> upsertItemDiscount(@Valid @RequestBody DiscountDTO itemDiscountDTO) {
        return this.itemService.updateItemDiscount(itemDiscountDTO);
    }

    /**
     * Add or update an item discount for an item in the database
     */
    @PostMapping("/create-item-discount")
    public ResponseEntity<Discount> createItemDiscount(@Valid @RequestBody DiscountCreationDTO discountCreationDTO) {
        return this.itemService.createItemDiscount(discountCreationDTO);
    }

    /**
     * Add or update an item discount slab for a discount in the database
     */
    @PutMapping("/change-discount-rule/{slabId}")
    public ResponseEntity<DiscountSlab> updateDiscountSlab(@Valid @RequestBody DiscountSlabDTO itemDiscountSlabDTO,
            @PathVariable Long slabId) {
        return this.itemService.updateDiscountSlab(itemDiscountSlabDTO, slabId);
    }

}