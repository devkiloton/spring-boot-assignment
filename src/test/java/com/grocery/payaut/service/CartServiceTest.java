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
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import com.grocery.payaut.dto.CartDTO;
import com.grocery.payaut.enumerator.ItemTypes;
import com.grocery.payaut.enumerator.MeasurementUnits;
import com.grocery.payaut.model.CartItem;
import com.grocery.payaut.model.Discount;
import com.grocery.payaut.model.DiscountSlab;
import com.grocery.payaut.model.Item;
import com.grocery.payaut.repository.ICartRepository;
import com.grocery.payaut.repository.IItemRepository;
import com.grocery.payaut.service.CartService;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @InjectMocks
    CartService cartService;

    @Mock
    ICartRepository cartRepository;

    @Spy
    IItemRepository itemRepository;

    CartDTO cartDTORecord = new CartDTO();

    Item itemRecord = new Item();

    CartItem cartItemRecord = new CartItem();

    @BeforeEach
    public void setUp() {
        Discount discount = new Discount();
        discount.setDiscountId(1L);
        discount.setIsConstantSlab(false);

        List<DiscountSlab> discountSlabs = new ArrayList<>();
        DiscountSlab discountSlab = new DiscountSlab();
        discountSlab.setUnitsToGetDiscount(1);
        discountSlab.setDiscountAmount(1);
        discountSlabs.add(discountSlab);

        discount.setDiscountSlabs(discountSlabs);

        this.itemRecord.setDiscount(discount);
        this.itemRecord.setName("Bread");
        this.itemRecord.setPrice(1.0);
        this.itemRecord.setType(ItemTypes.BREADS);
        this.itemRecord.setUnit(MeasurementUnits.PIECE);
        this.itemRecord.setCreatedAt(LocalDateTime.now().minusDays(7));

        this.cartItemRecord.setItemId(this.itemRecord.getItemId());
        this.cartItemRecord.setQuantity(1);
        this.cartDTORecord.setCartItems(Collections.singletonList(this.cartItemRecord));
    }

    @Test
    public void given_bread_created_7d_ago_when_postCheckoutCart_should_throw() {

        doReturn((Optional.of(this.itemRecord))).when(this.itemRepository).findById(this.itemRecord.getItemId());

        final ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> this.cartService.postCheckoutCart(this.cartDTORecord));

        assertEquals(exception.getMessage(), "412 PRECONDITION_FAILED \"Bread is too old to be sold! 🤮\"");

    }

    @Test
    public void given_bread_created_3d_ago_when_postCheckoutCart_should_not_throw() {

        this.itemRecord.setCreatedAt(LocalDateTime.now().minusDays(3));
        doReturn((Optional.of(this.itemRecord))).when(this.itemRepository).findById(this.itemRecord.getItemId());

        assertDoesNotThrow(() -> this.cartService.postCheckoutCart(this.cartDTORecord));
    }

}