package com.jumia.services;

import com.jumia.custom.models.Country;
import com.jumia.custom.models.State;
import com.jumia.entities.Customer;
import com.jumia.exceptions.BadSearchCriteriaException;
import com.jumia.exceptions.MinPageValueException;
import com.jumia.repos.CustomerRepo;
import com.jumia.services.interfaces.ISearchService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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

		Country phoneCode = getPhoneCode(countryName, countryISOCode);

		List<Customer> customers = new ArrayList<>();

		if (phoneCode != null) {
			customers.addAll(customerRepo.findByPhoneContaining(phoneCode.getPhoneCode()));
		} else {
			customers.addAll(customerRepo.findAll());
		}

		return filterByState(customers, state);
	}

	@Override
	public List<Customer> getCustomers(String countryName, String countryISOCode, State state, Integer page) {

		if (page < 1) {
			throw new MinPageValueException();
		}

		Country phoneCode = getPhoneCode(countryName, countryISOCode);

		List<Customer> customers = new ArrayList<>();

		if (phoneCode != null) {
			customers.addAll(customerRepo.findByPhoneContaining(phoneCode.getPhoneCode(), PageRequest.of(page - 1, 5)));
		} else {
			customers.addAll(customerRepo.findAll(PageRequest.of(page - 1, 5)));
		}

		return filterByState(customers, state);
	}

	Country getPhoneCode(String countryName, String countryISOCode) {

		if (Strings.isNotBlank(countryName) && Strings.isNotBlank(countryISOCode)) {
			throw new BadSearchCriteriaException();
		}

		Country phoneCode = null;

		if (Strings.isNotBlank(countryName)) {
			phoneCode = Country.fromCountryName(countryName);
		}

		if (Strings.isNotBlank(countryISOCode)) {
			phoneCode = Country.fromCountryISOCode(countryISOCode);
		}

		return phoneCode;
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
		Country country = Country.fromPhoneCode(code);
		String validPhoneRegex = country.getValidPhoneRegex();
		return customer.getPhone().matches(validPhoneRegex);
	}
}
