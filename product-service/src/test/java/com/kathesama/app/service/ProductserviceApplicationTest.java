package com.kathesama.app.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@ContextConfiguration(classes=ProductserviceApplication.class)
public class ProductserviceApplicationTest {

	@BeforeAll
	public void init() {}

	@Test
	public void contextLoads() {
	}
}
