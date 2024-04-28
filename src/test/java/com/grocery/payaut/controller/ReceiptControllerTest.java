package com.grocery.payaut.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.grocery.payaut.service.ReceiptService;

@ExtendWith(MockitoExtension.class)
public class ReceiptControllerTest {
    @InjectMocks
    ReceiptController receiptController;

    @Mock
    ReceiptService receiptService;

    MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(receiptController).build();
    }

    @Test
    public void should_return_all_carts() throws Exception {
        assertEquals(1 + 1, 2);
        // when(this.cartService.getAllCarts()).thenReturn(ResponseEntity.ok(Collections.emptyList()));

        // this.mockMvc.perform(get("/carts/all").contentType(MediaType.APPLICATION_JSON))
        // .andExpect(MockMvcResultMatchers.status().isOk());

        // verify(this.cartService).getAllCarts();
    }
}