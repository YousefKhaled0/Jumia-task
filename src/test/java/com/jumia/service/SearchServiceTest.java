package com.jumia.service;

import com.jumia.entities.Customer;
import com.jumia.services.SearchService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class SearchServiceTest {

	@InjectMocks
	SearchService searchService;

	@Test
	void test_invalid_phoneNumber() {
		Customer customer = new Customer();
		customer.setPhone("(212) 01061043275");
		assertFalse(searchService.isValid(customer));
	}

	@Test
	void test_valid_phoneNumber() {
		Customer customer = new Customer();
		customer.setPhone("(212) 654654536");
		assertTrue(searchService.isValid(customer));
	}
}
