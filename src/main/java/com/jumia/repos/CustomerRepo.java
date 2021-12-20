package com.jumia.repos;

import com.jumia.entities.Customer;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Set;

public interface CustomerRepo extends Repository<Customer, Integer> {

	List<Customer> findAll();
	List<Customer> findByPhoneContaining(String phoneCode);

}
