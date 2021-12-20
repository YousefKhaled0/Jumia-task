package com.jumia.controllers;

import com.jumia.custom.models.State;
import com.jumia.entities.Customer;
import com.jumia.services.interfaces.ISearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("api")
public class RestSearchController {

	@Autowired
	private ISearchService searchService;

	@GetMapping("/api/customers")
	public List<Customer> getCustomers(@RequestParam(name = "CountryName", required = false) String countryName,
			@RequestParam(name = "CountryISOCode", required = false) String countryISOCode,
			@RequestParam(name = "State", required = false) State state,
			@RequestParam(name = "page", required = false, defaultValue = "1") Integer page) {
		return searchService.getCustomers(countryName, countryISOCode, state, page);
	}

}
