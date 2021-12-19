package com.jumia.custom.models;

import com.jumia.exceptions.CountryISOCodeNotFoundException;
import com.jumia.exceptions.CountryNameNotFoundException;
import com.jumia.exceptions.PhoneCodeNotFoundException;
import com.jumia.util.CountryCodesUtil;

public enum CountryPhoneCode {

	// @formatter:off
	CAMEROON("Cameroon",CountryCodesUtil.getCountryISOCodeFormName("Cameroon"), "(237)", "\\(237\\) ?[2368]\\d{7,8}$"),
	ETHIOPIA("Ethiopia",CountryCodesUtil.getCountryISOCodeFormName("Ethiopia"), "(251)", "\\(251\\) ?[1-59]\\d{8}$"),
	MOROCCO("Morocco",CountryCodesUtil.getCountryISOCodeFormName("Morocco"), "(212)", "\\(212\\) ?[5-9]\\d{8}$"),
	MOZAMBIQUE("Mozambique",CountryCodesUtil.getCountryISOCodeFormName("Mozambique"), "(258)", "\\(258\\) ?[28]\\d{7,8}$"),
	UGANDA("Uganda",CountryCodesUtil.getCountryISOCodeFormName("Uganda"), "(256)", "\\(256\\) ?\\d{9}$");
	// @formatter:on

	private final String name;

	// The ISO code e.g. CM, ET, MA, MZ, UG
	private final String countryISOCode;

	private final String phoneCode;
	private final String validPhoneRegex;

	CountryPhoneCode(String name, String countryISOCode, String phoneCode, String validPhoneRegex) {
		this.name = name;
		this.countryISOCode = countryISOCode;
		this.phoneCode = phoneCode;
		this.validPhoneRegex = validPhoneRegex;
	}

	public String getName() {
		return name;
	}

	public String getCountryISOCode() {
		return countryISOCode;
	}

	public String getPhoneCode() {
		return phoneCode;
	}

	public String getValidPhoneRegex() {
		return validPhoneRegex;
	}

	public static CountryPhoneCode fromCountryName(String name) {
		CountryPhoneCode[] values = CountryPhoneCode.values();
		for (CountryPhoneCode countryPhoneCode : values) {
			if (countryPhoneCode.name.equals(name)) {
				return countryPhoneCode;
			}
		}

		throw new CountryNameNotFoundException(name);
	}

	public static CountryPhoneCode fromPhoneCode(String phoneCode) {
		CountryPhoneCode[] values = CountryPhoneCode.values();
		for (CountryPhoneCode countryPhoneCode : values) {
			if (countryPhoneCode.phoneCode.equals(phoneCode)) {
				return countryPhoneCode;
			}
		}

		throw new PhoneCodeNotFoundException(phoneCode);
	}

	public static CountryPhoneCode fromCountryISOCode(String countryCode) {
		CountryPhoneCode[] values = CountryPhoneCode.values();
		for (CountryPhoneCode countryPhoneCode : values) {
			if (countryPhoneCode.countryISOCode.equals(countryCode)) {
				return countryPhoneCode;
			}
		}

		throw new CountryISOCodeNotFoundException(countryCode);
	}

	@Override
	public String toString() {
		return name;
	}
}
