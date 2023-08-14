package com.code.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.code.entities.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
}
