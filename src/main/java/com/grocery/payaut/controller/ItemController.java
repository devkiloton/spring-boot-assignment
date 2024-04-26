package com.grocery.payaut.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grocery.payaut.dto.ItemDTO;
import com.grocery.payaut.dto.DiscountDTO;
import com.grocery.payaut.dto.DiscountSlabDTO;
import com.grocery.payaut.model.Discount;
import com.grocery.payaut.model.DiscountSlab;
import com.grocery.payaut.model.Item;
import com.grocery.payaut.service.ItemService;

@RestController
@RequestMapping("/api/v1")
public class ItemController {

    @Autowired
    private ItemService itemService;

    /**
     * Get all items in the database with its discounts
     * 
     * @return {@link List}
     */
    @GetMapping("/items")
    public List<Item> getItems() {
        return this.itemService.getAllItems();
    }

    /**
     * Add or update an item in the database
     */
    @PatchMapping("/change-item")
    public ResponseEntity<Item> upsertItem(@RequestBody ItemDTO item) {
        return this.itemService.upsertItem(item);
    }

    /**
     * Add or update an item discount for an item in the database
     */
    @PatchMapping("/change-item-discount")
    public ResponseEntity<Discount> upsertItemDiscount(@RequestBody DiscountDTO itemDiscountDTO) {
        return this.itemService.upsertItemDiscount(itemDiscountDTO);
    }

    /**
     * Add or update an item discount slab for a discount in the database
     */
    @PatchMapping("/change-discount-rule")
    public ResponseEntity<DiscountSlab> updateDiscountSlab(@RequestBody DiscountSlabDTO itemDiscountSlabDTO) {
        return this.itemService.updateDiscountSlab(itemDiscountSlabDTO);
    }

}