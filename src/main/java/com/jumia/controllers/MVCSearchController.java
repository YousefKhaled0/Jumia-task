package com.jumia.controllers;

import com.jumia.custom.models.State;
import com.jumia.entities.Customer;
import com.jumia.services.interfaces.ISearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MVCSearchController {

	@Autowired
	private ISearchService searchService;

	@GetMapping("/customers")
	public String getCustomers(@RequestParam(name = "CountryName", required = false) String countryName,
			@RequestParam(name = "CountryISOCode", required = false) String countryISOCode,
			@RequestParam(name = "State", required = false) State state, ModelMap modelMap) {

		List<Customer> customers = searchService.getCustomers(countryName, countryISOCode, state);
		modelMap.addAttribute("isEmpty", customers.isEmpty());
		modelMap.addAttribute("customers", customers);
		return "index";

	}

	@GetMapping("/")
	public String home() {
		return "redirect:/customers";
	}

}
