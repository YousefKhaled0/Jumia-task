package com.jumia.exceptions;

public class BadSearchCriteriaException extends RuntimeException {
	public BadSearchCriteriaException() {
		super("Either country name or iso code should be present not both.");
	}
}
