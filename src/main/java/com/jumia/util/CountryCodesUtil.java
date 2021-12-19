package com.jumia.util;

import java.util.Locale;

public class CountryCodesUtil {

	private CountryCodesUtil() {
		throw new UnsupportedOperationException();
	}

	public static String getCountryISOCodeFormName(String name) {
		for (String isoCode : Locale.getISOCountries()) {
			Locale locale = new Locale("", isoCode);
			if (locale.getDisplayCountry().equals(name)) {
				return isoCode;
			}
		}

		throw new IllegalArgumentException("Country ISO code not found.");
	}

}
