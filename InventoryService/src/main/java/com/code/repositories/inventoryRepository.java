package com.code.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.code.entities.Inventory;

@Repository
public interface inventoryRepository extends JpaRepository<Inventory, Long> {


	List<Inventory> findBySkuCodeIn(List<String> skucode);

}
