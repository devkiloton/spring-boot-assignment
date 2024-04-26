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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long discountId;
    @Column(name = "is_constant_slab")
    private Boolean isConstantSlab;
    @Enumerated(EnumType.STRING)
    @Column(name = "discount_unit")
    private DiscountUnits discountUnit;
    @OneToMany(mappedBy = "discount", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<DiscountSlab> discountSlabs;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "discount")
    @JsonIgnore
    private Item item;
}
