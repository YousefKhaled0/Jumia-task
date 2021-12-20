package com.jumia.services;

import com.jumia.custom.models.CountryPhoneCode;
import com.jumia.custom.models.State;
import com.jumia.entities.Customer;
import com.jumia.exceptions.BadSearchCriteriaException;
import com.jumia.repos.CustomerRepo;
import com.jumia.services.interfaces.ISearchService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SearchService implements ISearchService {

	@Autowired
	private CustomerRepo customerRepo;

	@Override
	public List<Customer> getCustomers(String countryName, String countryISOCode, State state) {
		if (Strings.isNotBlank(countryName) && Strings.isNotBlank(countryISOCode)) {
			throw new BadSearchCriteriaException();
		}

		CountryPhoneCode phoneCode = null;

		if (Strings.isNotBlank(countryName)) {
			phoneCode = CountryPhoneCode.fromCountryName(countryName);
		}

		if (Strings.isNotBlank(countryISOCode)) {
			phoneCode = CountryPhoneCode.fromCountryISOCode(countryISOCode);
		}

		List<Customer> customers = new ArrayList<>();

		if (phoneCode != null) {
			customers.addAll(customerRepo.findByPhoneContaining(phoneCode.getPhoneCode()));
		} else {
			customers.addAll(customerRepo.findAll());
		}

		return filterByState(customers, state);
	}

	List<Customer> filterByState(List<Customer> customers, State state) {
		// @formatter:off
		if (state == null) return customers;
		else if (state == State.VALID)
			return customers.stream().filter(this::isValid)
					.collect(Collectors.toList());
		else return customers.stream().filter(customer -> !isValid(customer))
					.collect(Collectors.toList());
		// @formatter:on
	}

	boolean isValid(Customer customer) {
		String code = customer.getPhone().substring(0, 5);
		CountryPhoneCode countryPhoneCode = CountryPhoneCode.fromPhoneCode(code);
		String validPhoneRegex = countryPhoneCode.getValidPhoneRegex();
		return customer.getPhone().matches(validPhoneRegex);
	}
}
