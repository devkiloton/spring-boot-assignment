package com.grocery.payaut.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grocery.payaut.model.DiscountSlab;

public interface IDiscountSlabRepository extends JpaRepository<DiscountSlab, Long> {

}