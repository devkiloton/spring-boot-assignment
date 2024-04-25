package com.grocery.payaut.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grocery.payaut.model.Discount;

public interface IDiscountRepository extends JpaRepository<Discount, Long> {

}