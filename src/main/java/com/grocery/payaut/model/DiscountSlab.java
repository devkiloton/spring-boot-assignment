package com.grocery.payaut.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "discount_slab")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DiscountSlab {
    @Id
    private Long discountSlabId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discount_id")
    @JsonIgnore
    private Discount discount;
    @Column(name = "units_to_get_discount")
    private int unitsToGetDiscount;
    @Column(name = "discount_amount")
    private int discountAmount;
}