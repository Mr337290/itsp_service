package com.itsp.supplier.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.itsp.common.dao.BaseDao;
import com.itsp.supplier.entity.Order;

@Component
public class OrderDao extends BaseDao<Order> {
	public List<Order> getOrders(Long id) {
		DetachedCriteria dc = DetachedCriteria.forClass(Order.class);
		dc.add(Restrictions
				.sqlRestriction(
						"exists( select 1 from carrier_order_matcher r1 where r1.carrier_id = ? and {alias}.id = r1.order_id)",
						id, org.hibernate.type.LongType.INSTANCE));
		return this.findByCriteria(dc);
	}
}
