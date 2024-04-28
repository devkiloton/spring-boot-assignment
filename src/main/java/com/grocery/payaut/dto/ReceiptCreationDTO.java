package com.grocery.payaut.dto;

import java.util.List;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class ReceiptCreationDTO {
    @Valid
    @Size(min = 1, message = "Receipt items should have at least one item")
    private List<ReceiptItemCreationDTO> receiptItems;
}
