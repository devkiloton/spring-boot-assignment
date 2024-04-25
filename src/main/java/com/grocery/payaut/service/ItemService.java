package com.grocery.payaut.service;

import java.util.Optional;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.grocery.payaut.model.Item;
import com.grocery.payaut.repository.IItemRepository;

@Service
public class ItemService {

    @Autowired
    private IItemRepository itemRepository;

    public ResponseEntity<com.grocery.payaut.model.Item> upsertItem(Item item) {
        Optional<com.grocery.payaut.model.Item> itemOptional = itemRepository.findById(item.getItemId());
        if (itemOptional.isPresent()) {
            Item existingItem = itemOptional.get();
            // existingItem.setCreatedAt(item.getCreatedAt());
            // existingItem.setType(item.getType());
            Item savedItem = itemRepository.save(existingItem);
            return ResponseEntity.ok(savedItem);
        }

        Item savedItem = itemRepository.save(item);
        return ResponseEntity.ok(savedItem);
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }
}
