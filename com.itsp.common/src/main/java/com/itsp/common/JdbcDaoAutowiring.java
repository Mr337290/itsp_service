package com.itsp.common;

import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;
@Component
public class JdbcDaoAutowiring implements BeanPostProcessor{
	@Autowired
	private DataSource dataSourceLocal;

	@Override
	public Object postProcessAfterInitialization(Object bean, String name)
			throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String name)
			throws BeansException {
		 if ((bean instanceof JdbcDaoSupport) && (((JdbcDaoSupport)bean).getDataSource() == null))
		      ((JdbcDaoSupport)bean).setDataSource(this.dataSourceLocal);
		    return bean;
	}

}
