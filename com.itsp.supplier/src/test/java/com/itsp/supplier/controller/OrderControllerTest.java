package com.itsp.supplier.controller;

import static junit.framework.Assert.assertEquals;

import java.lang.reflect.Method;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itsp.common.ItspJUnit4ClassRunner;
import com.itsp.common.dao.JdbcDao;
import com.itsp.supplier.entity.Order;

public class OrderControllerTest extends ItspJUnit4ClassRunner {
	@Autowired
	private OrderController orderController;
	@Autowired
	private JdbcDao jdbcDao;

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
		genergateOrder("1", "2", "1", 1, 6, "'755M'", "'755N'");
		genergateOrder("2", "1", "2", 2, 12, "'755A'", "'755C'");
		
		// when
		List<Order> orders = orderController.getOrders(1L);
		Order order = orders.get(0);
		
		// then
		assertEquals(1, orders.size());
		assertEquals("755M", order.getOrigin());
		assertEquals("755N", order.getTarget());
//		assertEquals("", order.getCreateTime());
//		assertEquals("", order.getStartDate());
//		assertEquals("", order.getEndDate());
		assertEquals(6, order.getVehicleAge());
		assertEquals(1, order.getVehicleType());
	}

	private void genergateOrder(String matcherId, String orderId, String carrierId, int vehicleType, int vehicleAge, String origin, String target) {
		createOrderMatcher(matcherId, carrierId, orderId);
		
		createOrder(orderId, origin, target, String.valueOf(vehicleType), String.valueOf(vehicleAge));
	}
	
	private void createOrderMatcher(String... values) {
		String sqlForOrderMatcherGeneration = "insert into carrier_order_matcher(id,carrier_id,order_id) values(%s,%s,%s)";
		
		jdbcDao.getJdbcTemplate().execute(String.format(sqlForOrderMatcherGeneration, values[0], values[1], values[2]));
	}
	
	private void createOrder(String...values) {
		String sqlForOrderGeneration = "insert into carrier_order(id,end_date,create_time,start_date,origin,target,vehicle_type,vehicle_age) "
			+ "values(%s,%s,%s,%s,%s,%s,%s,%s)";
		jdbcDao.getJdbcTemplate().execute(String.format(sqlForOrderGeneration, values[0], 
				"CURRENT_DATE()", "CURRENT_DATE()", "CURRENT_DATE()", values[1], values[2], values[3], values[4]));
	}
}
