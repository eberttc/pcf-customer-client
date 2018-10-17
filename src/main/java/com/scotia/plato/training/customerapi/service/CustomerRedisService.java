package com.scotia.plato.training.customerapi.service;

import com.google.gson.Gson;
import com.scotia.plato.training.customerapi.dao.RedisClientTemplate;
import com.scotia.plato.training.customerapi.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerRedisService {

	@Autowired
	private RedisClientTemplate redisClientTemplate;

	public Customer createCustomer(Customer customer){
		customer.setId(UUID.randomUUID().toString());
		Gson gson = new Gson();
		String json = gson.toJson(customer);
		redisClientTemplate.set(customer.getId(),json);
		return customer;
	}

	public Customer getCustomer(String id){
		Gson gson = new Gson();
		String json = redisClientTemplate.get(id);
		Customer object=gson.fromJson(json, Customer.class);
		return object;
	}
}
