package com.grocery.payaut.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

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
    @Column(name = "discount_unit")
    private String discountUnit;
    @OneToMany(mappedBy = "discount", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<DiscountSlab> discountSlabs;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "discount")
    @JsonIgnore
    private Item item;
}
