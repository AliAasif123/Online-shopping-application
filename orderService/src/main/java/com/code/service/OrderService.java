package com.code.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.code.dto.InventoryResponse;
import com.code.dto.OrderLineItemsDto;
import com.code.dto.OrderRequest;
import com.code.entity.Order;
import com.code.entity.OrderLineItems;
import com.code.repositories.orderRepositories;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	private final orderRepositories orderRepositories;
	private final WebClient webclient;

	public void placedOlder(OrderRequest orderRequest) {
		Order order = new Order();
		order.setOrderNumber(UUID.randomUUID().toString());

		List<OrderLineItems> collect = orderRequest.getOrderLineItemsDtoList().stream().map(this::mapToDto)
				.collect(Collectors.toList());
		order.setOrderLineItemsList(collect);

		// call inventory service and place order if product is in stock
		List<String> skucodes = order.getOrderLineItemsList().stream().map(OrderLineItems::getSkuCode)
				.collect(Collectors.toList());
		InventoryResponse[] inventoryResponsesArray = null;
		try {
			inventoryResponsesArray = webclient.get()
					.uri("http://localhost:9092/api/inventory",
							UriBuilder -> UriBuilder.queryParam("skuCode", skucodes).build())
					.retrieve().bodyToMono(InventoryResponse[].class).block();
		} catch (Exception e) {
			e.printStackTrace();
		}
		boolean result = Arrays.stream(inventoryResponsesArray).allMatch(InventoryResponse::isInStock);
		if (result) {
			orderRepositories.save(order);
		} else {
			throw new IllegalArgumentException(
					"product that you try to find which is not present in inventory_stock!!");
		}
	}

	private OrderLineItems mapToDto(OrderLineItemsDto orderlineitemdto) {
		OrderLineItems orderLineItems = new OrderLineItems();
		orderLineItems.setPrice(orderlineitemdto.getPrice());
		orderLineItems.setQuantity(orderlineitemdto.getQuantity());
		orderLineItems.setSkuCode(orderlineitemdto.getSkuCode());
		return orderLineItems;

	}

}
