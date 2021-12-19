package com.jumia.utils;

import com.jumia.exceptions.CountryISOCodeNotFoundException;
import com.jumia.exceptions.CountryNameNotFoundException;
import com.jumia.exceptions.PhoneCodeNotFoundException;
import com.jumia.custom.models.CountryPhoneCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CountryPhoneCodeTest {

	public static Stream<Arguments> testFromCountryName() {
		// @formatter:off
			return Stream.of(
					Arguments.of("Cameroon", CountryPhoneCode.CAMEROON),
					Arguments.of("Ethiopia", CountryPhoneCode.ETHIOPIA),
					Arguments.of("Morocco", CountryPhoneCode.MOROCCO),
					Arguments.of("Mozambique", CountryPhoneCode.MOZAMBIQUE),
					Arguments.of("Uganda", CountryPhoneCode.UGANDA)
			);
			// @formatter:on
	}

	public static Stream<Arguments> testFromCountryISOCode() {
		// @formatter:off
		return Stream.of(
				Arguments.of( "CM" , CountryPhoneCode.CAMEROON),
				Arguments.of( "ET" , CountryPhoneCode.ETHIOPIA),
				Arguments.of( "MA" , CountryPhoneCode.MOROCCO),
				Arguments.of( "MZ" , CountryPhoneCode.MOZAMBIQUE),
				Arguments.of( "UG" , CountryPhoneCode.UGANDA)
		);
		// @formatter:on
	}

	public static Stream<Arguments> testFromCountryPhoneCode() {
		// @formatter:off
		return Stream.of(
				Arguments.of( "(237)" , CountryPhoneCode.CAMEROON),
				Arguments.of( "(251)" , CountryPhoneCode.ETHIOPIA),
				Arguments.of( "(212)" , CountryPhoneCode.MOROCCO),
				Arguments.of( "(258)" , CountryPhoneCode.MOZAMBIQUE),
				Arguments.of( "(256)" , CountryPhoneCode.UGANDA)
		);
		// @formatter:on
	}

	@Test
	void testCountryNameNotFound() {
		assertThrows(CountryNameNotFoundException.class, () -> CountryPhoneCode.fromCountryName("Egypt"));
	}

	@Test
	void testCountryISOCodeNotFound() {
		assertThrows(CountryISOCodeNotFoundException.class, () -> CountryPhoneCode.fromCountryISOCode("EG"));
	}

	@Test
	void testCountryPhoneCodeNotFound() {
		assertThrows(PhoneCodeNotFoundException.class, () -> CountryPhoneCode.fromPhoneCode("(020)"));
	}

	@ParameterizedTest
	@MethodSource
	void testFromCountryName(String name, CountryPhoneCode country) {
		assertEquals(CountryPhoneCode.fromCountryName(name), country);
	}

	@ParameterizedTest
	@MethodSource
	void testFromCountryISOCode(String isoCode, CountryPhoneCode country) {
		assertEquals(CountryPhoneCode.fromCountryISOCode(isoCode), country);
	}

	@ParameterizedTest
	@MethodSource
	void testFromCountryPhoneCode(String phoneCode, CountryPhoneCode country) {
		assertEquals(CountryPhoneCode.fromPhoneCode(phoneCode), country);
	}

}
