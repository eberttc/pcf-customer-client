package com.scotia.plato.training.customerapi.controller;


import com.scotia.plato.training.customerapi.service.Greeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private Greeting greeting;

	@GetMapping("/hello")
	public String sayHello(){
		return "Hello Customer "+greeting.getGreeting();
	}
}
