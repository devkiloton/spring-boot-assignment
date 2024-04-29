package com.grocery.payaut.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.grocery.payaut.enumerator.DiscountUnits;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "discount")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long discountId;

    /**
     * Constant slab means that for every X units, the discount will be Y, where X
     * and Y are constant values. So we can use 1 slab for all the items.
     */
    @Column(name = "is_constant_slab")
    private Boolean isConstantSlab;

    /**
     * Property to store the discount unit. e.g. PERCENTAGE, AMOUNT, etc.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "discount_unit")
    private DiscountUnits discountUnit;

    /**
     * A discount slab refers to a condition.
     * e.g. Discount X is applied when the user buys Y units
     */
    @OneToMany(mappedBy = "discount", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<DiscountSlab> discountSlabs;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "discount")
    @JsonIgnore
    private Item item;
}
