package com.jumia.services;

import com.jumia.custom.models.State;
import com.jumia.entities.Customer;
import com.jumia.exceptions.BadSearchCriteriaException;
import com.jumia.services.interfaces.ISearchService;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class SearchService implements ISearchService {

	@Override
	public List<Customer> getCustomers(String countryName, String countryISOCode, State state) {
		if (countryName != null && countryISOCode != null) {
			throw new BadSearchCriteriaException();
		}

		return Collections.emptyList();
	}
}
