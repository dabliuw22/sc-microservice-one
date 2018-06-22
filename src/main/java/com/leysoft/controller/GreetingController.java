package com.leysoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leysoft.dto.GreetingRequest;
import com.leysoft.dto.GreetingResponse;
import com.leysoft.service.inter.GreetingService;

@RestController
@RequestMapping(value = {"/greeting"})
public class GreetingController {
	
	@Autowired
	private GreetingService greetingService;
	
	@GetMapping(value = {"/{name}"})
	public GreetingResponse greeting(@PathVariable(name = "name") String name) {
		GreetingRequest request = new GreetingRequest();
		request.setName(name);
		return greetingService.greeting(request);
	}
}
