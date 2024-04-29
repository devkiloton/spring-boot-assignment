package com.grocery.payaut.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.grocery.payaut.dto.ItemDTO;
import com.grocery.payaut.mapper.ICustomMappers;
import com.grocery.payaut.dto.DiscountCreationDTO;
import com.grocery.payaut.dto.DiscountDTO;
import com.grocery.payaut.dto.DiscountSlabDTO;
import com.grocery.payaut.dto.ItemCreationDTO;
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

    @Autowired
    private ICustomMappers mappers;

    public ResponseEntity<List<Item>> getAllItems() {
        return ResponseEntity.ok(this.itemRepository.findAll());
    }

    public ResponseEntity<Item> updateItem(ItemDTO itemDTO) {
        Item item = this.itemRepository.findById(itemDTO.getItemId()).get();
        this.mappers.dtoToItem(itemDTO, item);
        Item savedItem = this.itemRepository.save(item);
        return ResponseEntity.ok(savedItem);
    }

    /**
     * Creates a new item to the database, so users can buy it
     * 
     * @param itemCreationDTO - Object that contains the item's information
     */
    public ResponseEntity<Item> createItem(ItemCreationDTO itemCreationDTO) {
        Item newItem = new Item();
        final DiscountCreationDTO discountCreatinoDTO = itemCreationDTO.getDiscount();

        this.mappers.dtoToItemCreation(itemCreationDTO, newItem);
        // Discount should be null since it's a relationship, otherwise there will be an
        // error
        newItem.setDiscount(null);
        Item savedItem = this.itemRepository.save(newItem);

        // Save discount
        discountCreatinoDTO.setItemId(savedItem.getItemId());
        this.createItemDiscount(discountCreatinoDTO);
        return ResponseEntity.ok(savedItem);
    }

    public ResponseEntity<Discount> updateItemDiscount(DiscountDTO itemdDiscountDTO) {
        Discount discount = this.discountRepository.findById(itemdDiscountDTO.getDiscountId()).get();

        this.mappers.dtoToDiscount(itemdDiscountDTO, discount);
        Discount savedDiscount = this.discountRepository.save(discount);
        return ResponseEntity.ok(savedDiscount);
    }

    public ResponseEntity<Discount> createItemDiscount(DiscountCreationDTO discountCreationDTO) {
        Discount newDiscount = new Discount();

        // Mapping slabs
        List<DiscountSlab> discountSlabs = discountCreationDTO.getDiscountSlabs().stream().map(slab -> {
            DiscountSlab discountSlab = new DiscountSlab();
            discountSlab.setDiscount(newDiscount);
            discountSlab.setUnitsToGetDiscount(slab.getUnitsToGetDiscount());
            discountSlab.setDiscountAmount(slab.getDiscountAmount());
            return discountSlab;
        }).toList();

        this.mappers.dtoToDiscountCreation(discountCreationDTO, newDiscount);
        newDiscount.setDiscountSlabs(discountSlabs);
        Discount savedDiscount = this.discountRepository.save(newDiscount);

        // Updating item with discount because of the relationship and to have cascade
        this.itemRepository.findById(discountCreationDTO.getItemId()).ifPresent(item -> {
            item.setDiscount(savedDiscount);
            this.itemRepository.save(item);
        });
        return ResponseEntity.ok(savedDiscount);
    }

    /**
     * A discount slab refers to a condition.
     * e.g. Discount X is applied when the user buys Y units
     * 
     * @param itemDiscountSlabDTO - Object that contains the `unitsToGetDiscount`
     *                            and `discountAmount`
     * @param slabId              - The id of the discount slab to be updated
     * @return an Updated DiscountSlab
     */
    public ResponseEntity<DiscountSlab> updateDiscountSlab(DiscountSlabDTO itemDiscountSlabDTO, Long slabId) {
        DiscountSlab discountSlab = this.discountSlabRepository.findById(slabId).get();

        this.mappers.dtoToDiscountSlab(itemDiscountSlabDTO, discountSlab);
        DiscountSlab savedDiscountSlab = this.discountSlabRepository.save(discountSlab);
        return ResponseEntity.ok(savedDiscountSlab);
    }
}
