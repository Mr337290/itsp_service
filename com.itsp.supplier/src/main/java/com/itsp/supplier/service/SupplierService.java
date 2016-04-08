package com.itsp.supplier.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itsp.supplier.dao.OrderDao;
import com.itsp.supplier.entity.Order;

@Component
public class SupplierService {
	@Autowired
	private OrderDao supplierDao;

	public List<Order> getOrders(Long id) {
		return supplierDao.getOrders(id);
	}
}
