package com.jumia.services;

import com.jumia.custom.models.State;
import com.jumia.entities.Customer;
import com.jumia.exceptions.BadSearchCriteriaException;
import com.jumia.preparation.CustomersGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SearchServiceTest {

	@InjectMocks
	SearchService searchService;

	@Test
	void test_either_country_name_or_ISO_code() {
		assertThrows(BadSearchCriteriaException.class, () -> searchService.getCustomers("Cameroon", "UG", null));
	}

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

	@Test
	void test_valid_phoneNumbers() {
		List<Customer> customers = CustomersGenerator.twoValidOneInvalid();
		assertEquals(2, searchService.filterByState(customers, State.VALID).size());
	}

	@Test
	void test_invalid_phoneNumbers() {
		List<Customer> customers = CustomersGenerator.twoValidOneInvalid();
		assertEquals(1, searchService.filterByState(customers, State.INVALID).size());
	}

	@Test
	void test_null_state_get_all_phoneNumbers() {
		List<Customer> customers = CustomersGenerator.twoValidOneInvalid();
		assertEquals(3, searchService.filterByState(customers, null).size());
	}

}
