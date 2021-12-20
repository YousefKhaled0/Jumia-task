package com.jumia.preparation;

import com.jumia.entities.Customer;

import java.util.Arrays;
import java.util.List;

public class CustomersGenerator {

	private CustomersGenerator() {
		throw new UnsupportedOperationException();
	}

	public static List<Customer> customers() {
		Customer customer1 = new Customer();
		customer1.setId(0);
		customer1.setName("Houda Houda");
		customer1.setPhone("(212) 6617344445");

		Customer customer2 = new Customer();
		customer2.setId(1);
		customer2.setName("Ogwal David");
		customer2.setPhone("(256) 7771031454");

		Customer customer3 = new Customer();
		customer3.setId(2);
		customer3.setName("Walla's Singz Junior");
		customer3.setPhone("(258) 846565883");

		return Arrays.asList(customer1, customer2, customer3);
	}

	public static List<Customer> twoValidOneInvalid() {
		Customer customer1 = new Customer();
		customer1.setId(0);
		customer1.setName("Houda Houda");
		customer1.setPhone("(212) 654654536");

		Customer customer2 = new Customer();
		customer2.setId(1);
		customer2.setName("Ogwal David");
		customer2.setPhone("(212) 654654537");

		Customer customer3 = new Customer();
		customer3.setId(2);
		customer3.setName("Walla's Singz Junior");
		customer3.setPhone("(212) (212) 6546545360000");

		return Arrays.asList(customer1, customer2, customer3);
	}
}
