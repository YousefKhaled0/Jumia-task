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

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SearchService implements ISearchService {

	@Autowired
	private CustomerRepo customerRepo;

	@Override
	public List<Customer> getCustomers(String countryName, String countryISOCode, State state) {

		Country country = getCountryFromRequest(countryName, countryISOCode);

		if (country == null) {
			if (state == null) {
				return customerRepo.findAll();
			} else
				return filterByState(customerRepo.findAll(), state);
		} else {
			if (state == null) {
				return customerRepo.findByPhoneContaining(country.getPhoneCode());
			} else
				return filterByState(customerRepo.findByPhoneContaining(country.getPhoneCode()), state);
		}
	}

	@Override
	public List<Customer> getCustomers(String countryName, String countryISOCode, State state, Integer page) {

		if (page < 1) {
			throw new MinPageValueException();
		}

		Country country = getCountryFromRequest(countryName, countryISOCode);

		if (country == null) {
			if (state == null) {
				return customerRepo.findAll(PageRequest.of(page - 1, 5));
			} else {
				return filterByState(customerRepo.findAll(), state, page);
			}
		} else {
			if (state == null) {
				return customerRepo.findByPhoneContaining(country.getPhoneCode(), PageRequest.of(page - 1, 5));
			} else {
				return filterByState(customerRepo.findByPhoneContaining(country.getPhoneCode()), state, page);
			}
		}
	}

	List<Customer> filterByState(List<Customer> customers, State state, Integer page) {
		List<Customer> customersByState = filterByState(customers, state);

		int startIndex = Math.min(customersByState.size(), (page - 1) * 5);
		int endIndex = Math.min(startIndex + 5, customersByState.size());

		return customersByState.subList(startIndex, endIndex);
	}

	Country getCountryFromRequest(String countryName, String countryISOCode) {


		if (Strings.isNotBlank(countryName) && Strings.isNotBlank(countryISOCode)) {
			throw new BadSearchCriteriaException();
		}

		Country country = null;

		if (Strings.isNotBlank(countryName)) {
			country = Country.fromCountryName(countryName);
		}

		if (Strings.isNotBlank(countryISOCode)) {
			country = Country.fromCountryISOCode(countryISOCode);
		}

		return country;
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
