package com.jumia.services.interfaces;

import com.jumia.custom.models.State;
import com.jumia.entities.Customer;

import java.util.List;

public interface ISearchService {

	List<Customer> getCustomers(String countryName, String countryISOCode, State state);

	List<Customer> getCustomers(String countryName, String countryISOCode, State state, Integer page);
}
