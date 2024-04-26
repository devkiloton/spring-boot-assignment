package com.grocery.payaut.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.grocery.payaut.model.CartItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    private Long cartId;
    private List<CartItem> cartItems;
    private LocalDateTime cartDate;
}
