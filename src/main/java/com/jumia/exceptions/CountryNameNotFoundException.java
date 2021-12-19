package com.jumia.exceptions;

public class CountryNameNotFoundException extends RuntimeException {
	public CountryNameNotFoundException(String name) {
		super(String.format("Country name %s is either out of scope or wrong.", name));
	}
}
