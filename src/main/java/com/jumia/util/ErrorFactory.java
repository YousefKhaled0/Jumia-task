package com.jumia.util;

import com.jumia.custom.models.Error;

public class ErrorFactory {

	private ErrorFactory() {
		throw new UnsupportedOperationException();
	}

	public static Error error(String message, int status) {
		return new Error(message, status);
	}
}
