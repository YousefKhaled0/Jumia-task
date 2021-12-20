package com.jumia.controller;

import com.jumia.controllers.MVCSearchController;
import com.jumia.entities.Customer;
import com.jumia.exceptions.BadSearchCriteriaException;
import com.jumia.exceptions.CountryISOCodeNotFoundException;
import com.jumia.exceptions.CountryNameNotFoundException;
import com.jumia.exceptions.handler.MVCResponseExceptionHandler;
import com.jumia.preparation.CustomersGenerator;
import com.jumia.services.interfaces.ISearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(MockitoExtension.class)
class MVCSearchControllerTest {

	@Mock
	private ISearchService searchService;

	private MockMvc mvc;

	@InjectMocks
	private MVCSearchController MVCSearchController;

	private static final String CUSTOMERS_URI = "/customers";

	@BeforeEach
	public void setup() {

		// @formatter:off
		mvc = MockMvcBuilders.standaloneSetup(MVCSearchController)
				.setControllerAdvice(new MVCResponseExceptionHandler())
				.build();
		// @formatter:on
	}

	@Test
	void testValidResponseFromService_200_status() throws Exception {
		List<Customer> customers = CustomersGenerator.customers();

		when(searchService.getCustomers(any(), any(), any())).thenReturn(customers);

		// @formatter:off
		MockHttpServletResponse response = mvc
				.perform(get(CUSTOMERS_URI))
				.andDo(print()).andReturn().getResponse();

		// @formatter:on

		int status = response.getStatus();
		assertEquals(HttpStatus.OK.value(), status);

	}

	@Test
	void testValidResponseFromService_400_status_BadSearchCriteriaException() throws Exception {
		List<Customer> customers = CustomersGenerator.customers();

		when(searchService.getCustomers(any(), any(), any())).thenThrow(new BadSearchCriteriaException());

		// @formatter:off
		MockHttpServletResponse response = mvc
				.perform(get(CUSTOMERS_URI))
				.andDo(print()).andReturn().getResponse();

		// @formatter:on

		int status = response.getStatus();
		assertEquals(HttpStatus.BAD_REQUEST.value(), status);

	}

	@Test
	void testValidResponseFromService_400_status_CountryNameNotFoundException() throws Exception {
		List<Customer> customers = CustomersGenerator.customers();

		when(searchService.getCustomers(any(), any(), any())).thenThrow(new CountryNameNotFoundException("EGYPT"));

		// @formatter:off
		MockHttpServletResponse response = mvc
				.perform(get(CUSTOMERS_URI))
				.andDo(print()).andReturn().getResponse();

		// @formatter:on

		int status = response.getStatus();
		assertEquals(HttpStatus.BAD_REQUEST.value(), status);

	}

	@Test
	void testValidResponseFromService_400_status_CountryISOCodeNotFoundException() throws Exception {
		List<Customer> customers = CustomersGenerator.customers();

		when(searchService.getCustomers(any(), any(), any())).thenThrow(new CountryISOCodeNotFoundException("EG"));

		// @formatter:off
		MockHttpServletResponse response = mvc
				.perform(get(CUSTOMERS_URI))
				.andDo(print()).andReturn().getResponse();

		// @formatter:on

		int status = response.getStatus();
		assertEquals(HttpStatus.BAD_REQUEST.value(), status);

	}

}
