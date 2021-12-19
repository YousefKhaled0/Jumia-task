package com.jumia.exceptions;

public class CountryISOCodeNotFoundException extends RuntimeException {

	public CountryISOCodeNotFoundException(String countryCode) {
		super(String.format("Country iso code %s is either out of scope or wrong.", countryCode));
	}

}
