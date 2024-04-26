package com.grocery.payaut.service;

import java.util.Optional;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.grocery.payaut.dto.ItemDTO;
import com.grocery.payaut.dto.ItemDiscountDTO;
import com.grocery.payaut.dto.ItemDiscountSlabDTO;
import com.grocery.payaut.model.Discount;
import com.grocery.payaut.model.DiscountSlab;
import com.grocery.payaut.model.Item;
import com.grocery.payaut.repository.IDiscountRepository;
import com.grocery.payaut.repository.IDiscountSlabRepository;
import com.grocery.payaut.repository.IItemRepository;

@Service
public class ItemService {

    @Autowired
    private IItemRepository itemRepository;

    @Autowired
    private IDiscountRepository discountRepository;

    @Autowired
    private IDiscountSlabRepository discountSlabRepository;

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public ResponseEntity<Item> upsertItem(ItemDTO itemDTO) {
        if (itemDTO.getItemId() != null) {
            Optional<Item> itemOptional = itemRepository.findById(itemDTO.getItemId());
            Item existingItem = itemOptional.get();
            existingItem.setType(itemDTO.getType());
            existingItem.setUnit(itemDTO.getUnit());
            existingItem.setPrice(itemDTO.getPrice());
            existingItem.setDiscount(itemDTO.getDiscount());
            Item savedItem = itemRepository.save(existingItem);
            return ResponseEntity.ok(savedItem);
        }

        Item newItem = new Item();
        newItem.setType(itemDTO.getType());
        newItem.setUnit(itemDTO.getUnit());
        newItem.setPrice(itemDTO.getPrice());
        newItem.setDiscount(itemDTO.getDiscount());
        System.out.println("newItem: " + itemDTO.getDiscount());
        Item savedItem = itemRepository.save(newItem);
        return ResponseEntity.ok(savedItem);
    }

    public ResponseEntity<Discount> upsertItemDiscount(ItemDiscountDTO itemdDiscountDTO) {
        if (itemdDiscountDTO.getDiscountId() != null) {
            Optional<Discount> discountOptional = discountRepository.findById(itemdDiscountDTO.getDiscountId());
            Discount existingDiscount = discountOptional.get();
            existingDiscount.setDiscountSlabs(itemdDiscountDTO.getDiscountSlabs());
            existingDiscount.setDiscountUnit(itemdDiscountDTO.getDiscountUnit());
            existingDiscount.setIsConstantSlab(itemdDiscountDTO.getIsConstantSlab());
            Discount savedDiscount = discountRepository.save(existingDiscount);
            return ResponseEntity.ok(savedDiscount);
        }
        Discount newDiscount = new Discount();
        newDiscount.setDiscountSlabs(itemdDiscountDTO.getDiscountSlabs());
        newDiscount.setDiscountUnit(itemdDiscountDTO.getDiscountUnit());
        newDiscount.setIsConstantSlab(itemdDiscountDTO.getIsConstantSlab());
        Discount savedDiscount = discountRepository.save(newDiscount);
        return ResponseEntity.ok(savedDiscount);
    }

    public ResponseEntity<DiscountSlab> updateDiscountSlab(ItemDiscountSlabDTO itemDiscountSlabDTO) {
        Optional<DiscountSlab> discountSlabOptional = discountSlabRepository
                .findById(itemDiscountSlabDTO.getDiscountSlabId());
        DiscountSlab existingDiscountSlab = discountSlabOptional.get();
        existingDiscountSlab.setUnitsToGetDiscount(itemDiscountSlabDTO.getUnitsToGetDiscount());
        existingDiscountSlab.setDiscountAmount(itemDiscountSlabDTO.getDiscountAmount());
        DiscountSlab savedDiscountSlab = discountSlabRepository.save(existingDiscountSlab);
        return ResponseEntity.ok(savedDiscountSlab);
    }
}
