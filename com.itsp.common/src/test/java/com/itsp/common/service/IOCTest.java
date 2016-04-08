package com.itsp.common.service;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.itsp.common.ItspJUnit4ClassRunner;
import com.itsp.common.SessionFactionAutowiring;

public class IOCTest extends ItspJUnit4ClassRunner {
	@Autowired
	private ApplicationContextTest applicationContext;

	@Test
	public void APPLICATION_CONTEXT_IS_RUNNING() {
		Assert.assertNotNull("上下文寄存对象不能为空", applicationContext);
		Assert.assertNotNull("上下文不能为空",
				applicationContext.getApplicationContext());
	}
	@Test
	public void BEAN_IS_INIT() {
		Assert.assertNotNull("bean 后置事件为成功初始化", applicationContext
				.getApplicationContext()
				.getBean(SessionFactionAutowiring.class));
	}
}
