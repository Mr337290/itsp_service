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
		createOrder(DateUtil.autoFormat("2016-04-07 15:28:00"),
				DateUtil.autoFormat("2016-04-07 15:29:00"),
				DateUtil.autoFormat("2016-04-07 15:30:00"), "020A", "020B", 1,
				10);

		Order order2 = createOrder(DateUtil.autoFormat("2016-04-08 15:28:00"),
				DateUtil.autoFormat("2016-04-08 15:29:00"),
				DateUtil.autoFormat("2016-04-08 15:30:00"), "020C", "020D", 2,
				12);

		OrderMatcher orderMatcher = cretaeOrderMatcher(1L, order2.getId());

		// when
		List<Order> orders = orderController.getOrders(orderMatcher
				.getCarrierId());
		Order order = orders.get(0);

		// then
		assertEquals("size", 1, orders.size());
		assertEquals("Origin", "020C", order.getOrigin());
		assertEquals("Target", "020D", order.getTarget());
		assertEquals("CreateTime", "2016-04-08 15:28:00", DateUtil.string(
				order.getCreateTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
		assertEquals("StartDate", "2016-04-08 15:30:00", DateUtil.string(
				order.getStartDate(), DateUtil.YYYY_MM_DD_HH_MM_SS));
		assertEquals("EndDate", "2016-04-08 15:29:00", DateUtil.string(
				order.getEndDate(), DateUtil.YYYY_MM_DD_HH_MM_SS));
		assertEquals("VehicleAge", 2, order.getVehicleAge());
		assertEquals("VehicleType", 12, order.getVehicleType());
	}

	public Order createOrder(Date createTime, Date endDate, Date startDate,
			String origin, String target, int vehicleAge, int vehicleType) {
		Order order = new Order();
		order.setCreateTime(createTime);
		order.setEndDate(endDate);
		order.setStartDate(startDate);
		order.setOrigin(origin);
		order.setTarget(target);
		order.setVehicleAge(vehicleAge);
		order.setVehicleType(vehicleType);
		orderDao.save(order);
		return order;
	}

	public OrderMatcher cretaeOrderMatcher(Long carrierId, Long orderId) {
		OrderMatcher orderMatcher = new OrderMatcher();
		orderMatcher.setCarrierId(carrierId);
		orderMatcher.setOrderId(orderId);
		orderMatcherDao.save(orderMatcher);
		return orderMatcher;
	}
}
