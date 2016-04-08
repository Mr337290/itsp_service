package com.itsp.common;

import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

public class ItspJUnit4RunnerWith extends SpringJUnit4ClassRunner {

	public ItspJUnit4RunnerWith(Class<?> clazz) throws InitializationError {
		super(clazz);
	}
}
