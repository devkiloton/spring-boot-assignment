package com.grocery.payaut.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A discount slab refers to a condition.
 * e.g. Discount X (<code>unitsToGetDiscount</code> property) is applied when
 * the user buys Y(<code>discountAmount</code> property) units. An {@link Item}
 * can have multiple {@link DiscountSlab}.
 */
@Entity
@Table(name = "discount_slab")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DiscountSlab {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discount_id")
    @JsonIgnore
    private Discount discount;

    @Column(name = "units_to_get_discount")
    private Integer unitsToGetDiscount;

    @Column(name = "discount_amount")
    private Double discountAmount;
}