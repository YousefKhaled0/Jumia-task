package com.jumia.custom.models;

import java.util.Objects;

public class Error {
	private final String message;
	private final int status;

	public Error(String message, int status) {
		this.message = message;
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public int getStatus() {
		return status;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Error error = (Error) o;
		return status == error.status && Objects.equals(message, error.message);
	}

	@Override
	public int hashCode() {
		return Objects.hash(message, status);
	}

	@Override
	public String toString() {
		return "Error{" + "message='" + message + '\'' + ", status=" + status + '}';
	}
}
