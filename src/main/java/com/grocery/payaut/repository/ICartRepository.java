package com.grocery.payaut.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grocery.payaut.model.Cart;

public interface ICartRepository extends JpaRepository<Cart, Long> {

}