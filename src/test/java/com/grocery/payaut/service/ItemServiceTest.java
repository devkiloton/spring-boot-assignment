package com.grocery.payaut.service;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.grocery.payaut.enumerator.ItemTypes;
import com.grocery.payaut.enumerator.MeasurementUnits;
import com.grocery.payaut.mapper.ICustomMappers;
import com.grocery.payaut.model.Item;
import com.grocery.payaut.repository.IItemRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {

    @InjectMocks
    ItemService itemService;

    @Mock
    IItemRepository itemRepository;

    @Autowired
    ICustomMappers mappers;

    Item itemRecord = new Item();

    @BeforeEach
    public void setUp() {
        // Item
        this.itemRecord.setDiscount(null);
        this.itemRecord.setName("Deutsch Beer");
        this.itemRecord.setPrice(0.5);
        this.itemRecord.setType(ItemTypes.BEERS);
        this.itemRecord.setUnit(MeasurementUnits.PIECE);
        this.itemRecord.setId(1L);
    }

    @Test
    public void item_service_should_return_all_items_when_getAllItems_is_called() {
        when(this.itemRepository.findAll()).thenReturn(Collections.singletonList(this.itemRecord));

        ResponseEntity<List<Item>> items = this.itemService.getAllItems();

        assertEquals(Collections.singletonList(itemRecord), items.getBody());
        verify(itemRepository).findAll();
        verifyNoMoreInteractions(itemRepository);
    }

}