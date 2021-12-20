package com.jumia.utils;

import com.jumia.exceptions.CountryISOCodeNotFoundException;
import com.jumia.exceptions.CountryNameNotFoundException;
import com.jumia.exceptions.PhoneCodeNotFoundException;
import com.jumia.custom.models.Country;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CountryTest {

	public static Stream<Arguments> testFromCountryName() {
		// @formatter:off
			return Stream.of(
					Arguments.of("Cameroon", Country.CAMEROON),
					Arguments.of("Ethiopia", Country.ETHIOPIA),
					Arguments.of("Morocco", Country.MOROCCO),
					Arguments.of("Mozambique", Country.MOZAMBIQUE),
					Arguments.of("Uganda", Country.UGANDA)
			);
			// @formatter:on
	}

	public static Stream<Arguments> testFromCountryISOCode() {
		// @formatter:off
		return Stream.of(
				Arguments.of( "CM" , Country.CAMEROON),
				Arguments.of( "ET" , Country.ETHIOPIA),
				Arguments.of( "MA" , Country.MOROCCO),
				Arguments.of( "MZ" , Country.MOZAMBIQUE),
				Arguments.of( "UG" , Country.UGANDA)
		);
		// @formatter:on
	}

	public static Stream<Arguments> testFromCountryPhoneCode() {
		// @formatter:off
		return Stream.of(
				Arguments.of( "(237)" , Country.CAMEROON),
				Arguments.of( "(251)" , Country.ETHIOPIA),
				Arguments.of( "(212)" , Country.MOROCCO),
				Arguments.of( "(258)" , Country.MOZAMBIQUE),
				Arguments.of( "(256)" , Country.UGANDA)
		);
		// @formatter:on
	}

	@Test
	void testCountryNameNotFound() {
		assertThrows(CountryNameNotFoundException.class, () -> Country.fromCountryName("Egypt"));
	}

	@Test
	void testCountryISOCodeNotFound() {
		assertThrows(CountryISOCodeNotFoundException.class, () -> Country.fromCountryISOCode("EG"));
	}

	@Test
	void testCountryPhoneCodeNotFound() {
		assertThrows(PhoneCodeNotFoundException.class, () -> Country.fromPhoneCode("(020)"));
	}

	@ParameterizedTest
	@MethodSource
	void testFromCountryName(String name, Country country) {
		assertEquals(Country.fromCountryName(name), country);
	}

	@ParameterizedTest
	@MethodSource
	void testFromCountryISOCode(String isoCode, Country country) {
		assertEquals(Country.fromCountryISOCode(isoCode), country);
	}

	@ParameterizedTest
	@MethodSource
	void testFromCountryPhoneCode(String phoneCode, Country country) {
		assertEquals(Country.fromPhoneCode(phoneCode), country);
	}

}
