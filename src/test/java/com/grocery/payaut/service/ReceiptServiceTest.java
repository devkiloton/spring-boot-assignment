package com.grocery.payaut.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import com.grocery.payaut.dto.ReceiptCreationDTO;
import com.grocery.payaut.dto.ReceiptItemCreationDTO;
import com.grocery.payaut.enumerator.ItemTypes;
import com.grocery.payaut.enumerator.MeasurementUnits;
import com.grocery.payaut.model.Discount;
import com.grocery.payaut.model.DiscountSlab;
import com.grocery.payaut.model.Item;
import com.grocery.payaut.repository.IItemRepository;

@ExtendWith(MockitoExtension.class)
public class ReceiptServiceTest {

    @InjectMocks
    ReceiptService receiptService;

    @Spy
    IItemRepository itemRepository;

    ReceiptCreationDTO ReceiptCreationDTORecord = new ReceiptCreationDTO();

    Item itemRecord = new Item();

    @BeforeEach
    public void setUp() {
        // Discount
        Discount discount = new Discount();
        discount.setId(1L);
        discount.setIsConstantSlab(false);

        // Discount Slab
        List<DiscountSlab> discountSlabs = new ArrayList<>();
        DiscountSlab discountSlab = new DiscountSlab();
        discountSlab.setUnitsToGetDiscount(1);
        discountSlab.setDiscountAmount(1.0);
        discountSlabs.add(discountSlab);
        discount.setDiscountSlabs(discountSlabs);

        // Item
        this.itemRecord.setDiscount(discount);
        this.itemRecord.setName("Bread");
        this.itemRecord.setPrice(1.0);
        this.itemRecord.setType(ItemTypes.BREADS);
        this.itemRecord.setUnit(MeasurementUnits.PIECE);
        this.itemRecord.setCreatedAt(LocalDateTime.now().minusDays(7));
        this.itemRecord.setId(3L);

        // Receipt Item
        final ReceiptItemCreationDTO receiptItemCreationDTO = new ReceiptItemCreationDTO();
        receiptItemCreationDTO.setItemId(3L);
        receiptItemCreationDTO.setQuantity(1);
        this.ReceiptCreationDTORecord.setReceiptItems(Collections.singletonList(receiptItemCreationDTO));
    }

    @Test
    public void given_bread_created_7d_ago_when_postReceiptCreation_should_throw() {

        doReturn((Optional.of(this.itemRecord))).when(this.itemRepository).findById(this.itemRecord.getId());

        final ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> this.receiptService.postReceiptCreation(this.ReceiptCreationDTORecord));

        assertEquals(exception.getMessage(), "412 PRECONDITION_FAILED \"Bread is too old to be sold! 🤮\"");
    }

    @Test
    public void given_bread_created_3d_ago_when_postReceiptCreation_should_not_throw() {

        this.itemRecord.setCreatedAt(LocalDateTime.now().minusDays(3));
        doReturn((Optional.of(this.itemRecord))).when(this.itemRepository).findById(this.itemRecord.getId());

        assertDoesNotThrow(() -> this.receiptService.postReceiptCreation(this.ReceiptCreationDTORecord));
    }

}