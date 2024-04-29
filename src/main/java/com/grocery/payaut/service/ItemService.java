package com.grocery.payaut.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    /**
     * Returns all items from the database
     * 
     * @return {@link List} of {@link Item}
     */
    public ResponseEntity<List<Item>> getAllItems() {
        return ResponseEntity.ok(this.itemRepository.findAll());
    }

    /**
     * Updates an item in the database
     * 
     * @param itemDTO - Object that contains the item's information
     * @return an Updated {@link Item}
     */
    public ResponseEntity<Item> updateItem(ItemDTO itemDTO) {
        Item item = this.itemRepository.findById(itemDTO.getId()).get();
        this.mappers.dtoToItem(itemDTO, item);
        Item savedItem = this.itemRepository.save(item);
        return ResponseEntity.ok(savedItem);
    }

    /**
     * Creates a new item to the database, so users can buy it
     * 
     * @param itemCreationDTO - Object that contains the item's information
     * @return a new {@link Item}
     */
    public ResponseEntity<Item> createItem(ItemCreationDTO itemCreationDTO) {
        Item newItem = new Item();
        final DiscountCreationDTO discountCreationDTO = itemCreationDTO.getDiscount();

        this.mappers.dtoToItemCreation(itemCreationDTO, newItem);
        // Discount should be null since it's a relationship, otherwise there will be an
        // error
        newItem.setDiscount(null);
        Item savedItem = this.itemRepository.save(newItem);

        // Save discount
        if (discountCreationDTO != null) {
            discountCreationDTO.setId(savedItem.getId());
            this.createItemDiscount(discountCreationDTO);
        }
        return ResponseEntity.ok(savedItem);
    }

    /**
     * Updates an item discount
     * 
     * @param itemdDiscountDTO
     * @return an Updated {@link Discount}
     */
    public ResponseEntity<Discount> updateItemDiscount(DiscountDTO itemDiscountDTO) {

        Discount discount = this.discountRepository.findById(itemDiscountDTO.getId()).get();
        if (itemDiscountDTO.getDiscountSlabs() != null) {
            // Delete previous slabs
            this.discountSlabRepository.deleteAll(discount.getDiscountSlabs());

        }
        this.mappers.dtoToDiscount(itemDiscountDTO, discount);
        discount.getDiscountSlabs().forEach(slab -> {
            slab.setDiscount(discount);
        });
        Discount savedDiscount = this.discountRepository.save(discount);
        this.discountSlabRepository.deleteAll(discount.getDiscountSlabs());
        return ResponseEntity.ok(savedDiscount);
    }

    /**
     * Creates a new discount for an item
     * 
     * @param discountCreationDTO
     * @return a new {@link Discount}
     */
    public ResponseEntity<Discount> createItemDiscount(DiscountCreationDTO discountCreationDTO) {
        Discount newDiscount = new Discount();
        final Item possibleItem = this.itemRepository.findById(discountCreationDTO.getId()).get();
        if (possibleItem != null && possibleItem.getDiscount() == null) {
            // Mapping slabs
            List<DiscountSlab> discountSlabs = discountCreationDTO.getDiscountSlabs().stream().map(slab -> {
                DiscountSlab discountSlab = new DiscountSlab();
                discountSlab.setDiscount(newDiscount);
                discountSlab.setUnitsToGetDiscount(slab.getUnitsToGetDiscount());
                discountSlab.setDiscountAmount(slab.getDiscountAmount());
                return discountSlab;
            }).toList();

            // Setting discount properties
            newDiscount.setDiscountUnit(discountCreationDTO.getDiscountUnit());
            newDiscount.setIsConstantSlab(discountCreationDTO.getIsConstantSlab());
            newDiscount.setDiscountSlabs(discountSlabs);
            Discount savedDiscount = this.discountRepository.save(newDiscount);

            // Updating item with discount because of the relationship and to have cascade
            possibleItem.setDiscount(savedDiscount);
            this.itemRepository.save(possibleItem);
            return ResponseEntity.ok(savedDiscount);
        }
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                "Item doesn't exist or already has a discount");

    }

    /**
     * A discount slab refers to a condition.
     * e.g. Discount X is applied when the user buys Y units
     * 
     * @param itemDiscountSlabDTO - Object that contains the `unitsToGetDiscount`
     *                            and `discountAmount`
     * @param slabId              - The id of the discount slab to be updated
     * @return an Updated {@link DiscountSlab}
     */
    public ResponseEntity<DiscountSlab> updateDiscountSlab(DiscountSlabDTO itemDiscountSlabDTO, Long slabId) {
        DiscountSlab discountSlab = this.discountSlabRepository.findById(slabId).get();

        this.mappers.dtoToDiscountSlab(itemDiscountSlabDTO, discountSlab);
        DiscountSlab savedDiscountSlab = this.discountSlabRepository.save(discountSlab);
        return ResponseEntity.ok(savedDiscountSlab);
    }
}
