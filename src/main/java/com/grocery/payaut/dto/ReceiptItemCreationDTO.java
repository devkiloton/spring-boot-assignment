package com.grocery.payaut.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptItemCreationDTO {
    @Valid
    @NotNull
    private Integer quantity;
    @NotNull(message = "Item ID is required")
    private Long itemId;
}
