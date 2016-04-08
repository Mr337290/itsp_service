package com.itsp.supplier.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itsp.supplier.entity.Order;
import com.itsp.supplier.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private OrderService supplierService; 
	
	@RequestMapping(value = "/{id}")
	public List<Order>getOrders(@PathVariable("id") Long id) {
		return supplierService.getOrders(id);
	}
}