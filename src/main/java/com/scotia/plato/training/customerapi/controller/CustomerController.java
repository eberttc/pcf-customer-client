package com.scotia.plato.training.customerapi.controller;


import com.scotia.plato.training.customerapi.client.CustomerClient;
import com.scotia.plato.training.customerapi.domain.Customer;
import com.scotia.plato.training.customerapi.service.Greeting;
import com.scotia.plato.training.customerapi.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private Greeting greeting;

	@Autowired
	CustomerClient customerClient;

	@Autowired
	RedisService redisService;


	@GetMapping("/hello")
	public String sayHello(){
		return "Hello Customer "+greeting.getGreeting();
	}

	@GetMapping("/{id}")
	public Customer getCustomer(@PathVariable(name = "id") String id){
		return customerClient.getCustomer(id);
	}

	@PostMapping("/redis")
	public Customer saveCustomer(@RequestBody Customer customer){
		return redisService.createCustomer(customer);
	}

	@GetMapping("/redis/{id}")
	public Customer getCustomerRedis(@PathVariable(name = "id") String id){
		return redisService.getCustomer(id);
	}

}
