package com.code.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.code.dto.InventoryResponse;
import com.code.repositories.inventoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class InventoryService {

	private final inventoryRepository inventoryRepository;

	@Transactional(readOnly = true)
	public List<InventoryResponse> isInStock(List<String> skucode) {
		log.info("checking in inventory");
		List<InventoryResponse> collect = null;
		try {
			collect = inventoryRepository
					.findBySkuCodeIn(skucode).stream().map(inventorys -> InventoryResponse.builder()
							.skuCode(inventorys.getSkuCode()).isInStock(inventorys.getQuantity() > 0).build())
					.collect(Collectors.toList());

		} catch (Exception e) {
			e.getMessage();
		}
		return collect;

	}

}
