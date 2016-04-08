package com.itsp.common;

import org.hibernate.SessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;
@Component
public class SessionFactionAutowiring  implements BeanPostProcessor{
	@Autowired
	private SessionFactory sessionFactory;
		
	@Override
	public Object postProcessAfterInitialization(Object bean, String name)
			throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String name)
			throws BeansException {
		
		if ((bean instanceof HibernateDaoSupport) && (((HibernateDaoSupport)bean).getSessionFactory() == null))
		{
			System.out.println("");
			((HibernateDaoSupport)bean).setSessionFactory(this.sessionFactory);
		}
		return bean;
	}

}
