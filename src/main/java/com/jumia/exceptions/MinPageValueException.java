package com.jumia.exceptions;

public class MinPageValueException extends RuntimeException {

	public MinPageValueException(){
		super("Min value for page is 1");
	}

}
