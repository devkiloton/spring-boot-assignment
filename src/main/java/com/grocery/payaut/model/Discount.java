package com.grocery.payaut.model;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.grocery.payaut.enumerator.DiscountUnits;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "discount")
@Setter
@Getter
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long discountId;
    // Discount unit, e.g. $, %, pc etc.
    @Enumerated(EnumType.STRING)
    @Column(name = "discount_unit")
    private DiscountUnits discountUnit;
    @OneToMany(mappedBy = "discount", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<DiscountSlab> discountSlabs;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "discount")
    @JsonIgnore
    private Item item;
}
