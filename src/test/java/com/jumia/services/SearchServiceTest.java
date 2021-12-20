package com.jumia.services;

import com.jumia.custom.models.Country;
import com.jumia.custom.models.State;
import com.jumia.entities.Customer;
import com.jumia.exceptions.BadSearchCriteriaException;
import com.jumia.exceptions.MinPageValueException;
import com.jumia.preparation.CustomersGenerator;
import com.jumia.repos.CustomerRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SearchServiceTest {

	@Mock
	CustomerRepo customerRepo;

	@InjectMocks
	SearchService searchService;

	@Test
	void test_search_by_country_state_is_null() {
		when(customerRepo.findByPhoneContaining(eq(Country.MOROCCO.getPhoneCode()))).thenReturn(
				CustomersGenerator.twoValidOneInvalid());
		List<Customer> customers = searchService.getCustomers("Morocco", null, null);
		assertEquals(3, customers.size());
	}

	@Test
	void test_search_by_country_state_is_valid() {
		when(customerRepo.findByPhoneContaining(eq(Country.MOROCCO.getPhoneCode()))).thenReturn(
				CustomersGenerator.twoValidOneInvalid());
		List<Customer> customers = searchService.getCustomers("Morocco", null, State.VALID);
		assertEquals(2, customers.size());
	}

	@Test
	void test_search_by_country_state_is_inValid() {
		when(customerRepo.findByPhoneContaining(eq(Country.MOROCCO.getPhoneCode()))).thenReturn(
				CustomersGenerator.twoValidOneInvalid());
		List<Customer> customers = searchService.getCustomers("Morocco", null, State.INVALID);
		assertEquals(1, customers.size());
	}

	@Test
	void test_search_by_country_iso_code_state_is_null() {
		when(customerRepo.findByPhoneContaining(eq(Country.MOROCCO.getPhoneCode()))).thenReturn(
				CustomersGenerator.twoValidOneInvalid());
		List<Customer> customers = searchService.getCustomers(null, "MA", null);
		assertEquals(3, customers.size());
	}

	@Test
	void test_search_by_country_iso_code_state_is_valid() {
		when(customerRepo.findByPhoneContaining(eq(Country.MOROCCO.getPhoneCode()))).thenReturn(
				CustomersGenerator.twoValidOneInvalid());
		List<Customer> customers = searchService.getCustomers(null, "MA", State.VALID);
		assertEquals(2, customers.size());
	}

	@Test
	void test_search_by_country_state_iso_code_is_inValid() {
		when(customerRepo.findByPhoneContaining(eq(Country.MOROCCO.getPhoneCode()))).thenReturn(
				CustomersGenerator.twoValidOneInvalid());
		List<Customer> customers = searchService.getCustomers(null, "MA", State.INVALID);
		assertEquals(1, customers.size());
	}

	@Test
	void test_search_return_valid_phone_numbers() {
		when(customerRepo.findAll()).thenReturn(CustomersGenerator.twoValidOneInvalid());
		List<Customer> customers = searchService.getCustomers(null, null, State.VALID);
		assertEquals(2, customers.size());
	}

	@Test
	void test_search_return_invalid_phone_numbers() {
		when(customerRepo.findAll()).thenReturn(CustomersGenerator.twoValidOneInvalid());
		List<Customer> customers = searchService.getCustomers(null, null, State.INVALID);
		assertEquals(1, customers.size());
	}

	@Test
	void test_search_with_page_is_ok() {
		when(customerRepo.findAll(any())).thenReturn(CustomersGenerator.twoValidOneInvalid());
		List<Customer> customers = searchService.getCustomers(null, null, null, 1);
		assertEquals(3, customers.size());
	}

	@Test
	void test_page_less_than_1() {
		assertThrows(MinPageValueException.class, () -> searchService.getCustomers("Cameroon", "", null, -1));
	}

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

	@Test
	void test_filter_customers_with_paging() {
		List<Customer> data = new ArrayList<>();
		data.addAll(CustomersGenerator.twoValidOneInvalid());
		data.addAll(CustomersGenerator.twoValidOneInvalid());
		data.addAll(CustomersGenerator.twoValidOneInvalid());
		data.addAll(CustomersGenerator.twoValidOneInvalid());
		data.addAll(CustomersGenerator.twoValidOneInvalid());
		List<Customer> customersPageOne = searchService.filterByState(data, State.VALID, 1);
		List<Customer> customersPageTwo = searchService.filterByState(data, State.VALID, 2);
		List<Customer> customersPageThree = searchService.filterByState(data, State.VALID, 3);

		assertEquals(5, customersPageOne.size());
		assertEquals(5, customersPageTwo.size());
		assertEquals(0, customersPageThree.size());
	}

}
