package com.itsp.supplier.controller;

import static junit.framework.Assert.assertEquals;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itsp.common.ItspJUnit4ClassRunner;
import com.itsp.common.util.DateUtil;
import com.itsp.supplier.dao.OrderDao;
import com.itsp.supplier.dao.OrderMatcherDao;
import com.itsp.supplier.entity.Order;
import com.itsp.supplier.entity.OrderMatcher;

public class OrderControllerTest extends ItspJUnit4ClassRunner {
	@Autowired
	private OrderController orderController;
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private OrderMatcherDao orderMatcherDao;

	@Test
	public void getOrders_IS_MAP() {
		try {
			Method mehod = OrderController.class.getMethod("getOrders",
					Long.class);
			Assert.assertNotNull("getOrders方法没有映射路径",
					mehod.getAnnotation(RequestMapping.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Transactional
	@Rollback
	public void should_get_order() {

		// given
		Order newOrder1 = new Order();
		newOrder1.setCreateTime(new Date());
		newOrder1.setOrigin("020A");
		newOrder1.setTarget("020B");
		createOrder(newOrder1);

		Order newOrder2 = new Order();
		newOrder2.setCreateTime(new Date());
		newOrder2.setEndDate(new Date());
		newOrder2.setStartDate(new Date());
		newOrder2.setOrigin("020C");
		newOrder2.setTarget("020D");
		createOrder(newOrder2);

		OrderMatcher orderMatcher = new OrderMatcher();

		orderMatcher.setCarrierId(1L);
		orderMatcher.setOrderId(newOrder2.getId());

		cretaeOrderMatcher(orderMatcher);

		// when
		List<Order> orders = orderController.getOrders(orderMatcher
				.getCarrierId());
		Order order = orders.get(0);

		// then
		assertEquals("size", 1, orders.size());
		assertEquals("Origin", newOrder2.getOrigin(), order.getOrigin());
		assertEquals("Target", newOrder2.getTarget(), order.getTarget());
		assertEquals("CreateTime", DateUtil.string(newOrder2.getCreateTime(),
				DateUtil.YYYY_MM_DD_HH_MM_SS), DateUtil.string(
				order.getCreateTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
		assertEquals("StartDate", DateUtil.string(newOrder2.getStartDate(),
				DateUtil.YYYY_MM_DD_HH_MM_SS), DateUtil.string(
				order.getStartDate(), DateUtil.YYYY_MM_DD_HH_MM_SS));
		assertEquals("EndDate", DateUtil.string(newOrder2.getEndDate(),
				DateUtil.YYYY_MM_DD_HH_MM_SS), DateUtil.string(
				order.getEndDate(), DateUtil.YYYY_MM_DD_HH_MM_SS));
		assertEquals("VehicleAge", newOrder2.getVehicleAge(),
				order.getVehicleAge());
		assertEquals("VehicleType", newOrder2.getVehicleType(),
				order.getVehicleType());
	}

	public void createOrder(Order order) {
		orderDao.save(order);
	}
	public void cretaeOrderMatcher(OrderMatcher orderMatcher) {
		orderMatcherDao.save(orderMatcher);
	}
}
