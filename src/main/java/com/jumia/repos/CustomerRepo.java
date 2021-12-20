package com.jumia.repos;

import com.jumia.entities.Customer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface CustomerRepo extends Repository<Customer, Integer> {

	List<Customer> findAll();

	List<Customer> findByPhoneContaining(String phoneCode);

	List<Customer> findByPhoneContaining(String phoneCode, Pageable pageable);

	List<Customer> findAll(Pageable pageable);

}
