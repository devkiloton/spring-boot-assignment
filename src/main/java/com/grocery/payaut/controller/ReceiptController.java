package com.grocery.payaut.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grocery.payaut.dto.ReceiptCreationDTO;
import com.grocery.payaut.dto.ReceiptItemDTO;
import com.grocery.payaut.model.Item;
import com.grocery.payaut.service.ReceiptService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("receipts")
@Validated
public class ReceiptController {

    @Autowired
    private ReceiptService receiptService;

    /**
     * Generate a receipt for a list of items
     * 
     * @throws Exception if {@link Item} with <strong>itemType: BREADS</strong> with
     *                   creation date bigger than 6 days
     */
    @PostMapping("/checkout")
    public ResponseEntity<List<ReceiptItemDTO>> postReceiptCreation(
            @Valid @RequestBody ReceiptCreationDTO receiptCreationDTO) {
        return this.receiptService.postReceiptCreation(receiptCreationDTO);
    }

}