package com.jumia.utils;

import com.jumia.custom.models.Error;
import com.jumia.util.ErrorFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ErrorFactoryTest {

	@Test
	void createError() {
		Error error = ErrorFactory.error("abc", 400);
		assertEquals(400, error.getStatus());
		assertEquals("abc", error.getMessage());
	}
}
