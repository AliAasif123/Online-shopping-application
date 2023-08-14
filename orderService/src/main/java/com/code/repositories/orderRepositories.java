package com.code.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.code.entity.Order;

public interface orderRepositories extends JpaRepository<Order, Long> {

}
