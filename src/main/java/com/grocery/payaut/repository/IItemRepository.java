package com.grocery.payaut.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grocery.payaut.model.Item;

public interface IItemRepository extends JpaRepository<Item, Long> {

}