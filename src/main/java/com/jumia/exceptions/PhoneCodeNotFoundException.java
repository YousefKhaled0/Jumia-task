package com.jumia.exceptions;

public class PhoneCodeNotFoundException extends RuntimeException {

	public PhoneCodeNotFoundException(String phoneCode) {
		super(String.format("Phone code %s is either out of scope or wrong.", phoneCode));
	}
}
