package com.jumia.utils;

import com.jumia.util.CountryCodesUtil;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CountryCodesUtilTest {

	public static Stream<Arguments> testConversionFromCountryNameToCountryCode() {
		// @formatter:off
		return Stream.of(
				Arguments.of("Cameroon", "CM"),
				Arguments.of("Ethiopia", "ET"),
				Arguments.of("Morocco", "MA"),
				Arguments.of("Mozambique", "MZ"),
				Arguments.of("Uganda", "UG")
		);
		// @formatter:on
	}

	@ParameterizedTest
	@MethodSource
	void testConversionFromCountryNameToCountryCode(String name, String code) {
		assertEquals(CountryCodesUtil.getCountryISOCodeFormName(name), code);
	}

}
