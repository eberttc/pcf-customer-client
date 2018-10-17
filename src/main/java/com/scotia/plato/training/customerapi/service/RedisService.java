package com.scotia.plato.training.customerapi.service;

import com.google.gson.Gson;
import com.scotia.plato.training.customerapi.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RedisService {

	@Autowired
	@Qualifier("redisUserTemplate")
	private RedisTemplate<String, String> redisUserTemplate;

	public Customer createCustomer(Customer customer){
		customer.setId(UUID.randomUUID().toString());
		Gson gson = new Gson();
		String json = gson.toJson(customer);
		redisUserTemplate.opsForValue().set(customer.getId(),json);
		return customer;
	}

	public Customer getCustomer(String id){
		Gson gson = new Gson();
		String json = redisUserTemplate.opsForValue().get(id);
		Customer object=gson.fromJson(json, Customer.class);
		return object;
	}
}
