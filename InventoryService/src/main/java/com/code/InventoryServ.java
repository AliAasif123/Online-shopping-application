package com.code;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.code.entities.Inventory;
import com.code.repositories.inventoryRepository;

@SpringBootApplication()
//@RequiredArgsConstructor
public class InventoryServ {

	// private final inventoryRepository inventoryRepository;

	public static void main(String[] args) {
		SpringApplication.run(InventoryServ.class, args);
	}

	@Bean
	public CommandLineRunner loadData(inventoryRepository inventoryRepository) {
		return args -> {
			Inventory inventory = new Inventory();
			inventory.setSkuCode("iphone_13");
			inventory.setQuantity(100);

			Inventory inventory1 = new Inventory();
			inventory1.setSkuCode("samsung_214");
			inventory1.setQuantity(80);

			inventoryRepository.save(inventory);
			inventoryRepository.save(inventory1);

		};
	}
}
