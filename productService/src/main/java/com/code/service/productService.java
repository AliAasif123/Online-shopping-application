package com.code.service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.code.dto.ProductRequest;
import com.code.dto.ProductResponse;
import com.code.entities.Product;
import com.code.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class productService {

	private final ProductRepository productRepository;

	public void createProduct(ProductRequest productRequest) {
		Product product = Product.builder().name(productRequest.getName()).description(productRequest.getDescription())
				.price(productRequest.getPrice()).build();

		productRepository.save(product);
		log.info("Product {} is saved", product.getId());
	}

	public List<ProductResponse> getAllProduct() {
		List<Product> products = this.productRepository.findAll();

		return products.stream().map(this::mapToProductResponse).collect(Collectors.toList());

	}

	private ProductResponse mapToProductResponse(Product product) {
		return ProductResponse.builder().id(product.getId()).description(product.getDescription())
				.name(product.getName()).price(product.getPrice()).build();

	}

}
