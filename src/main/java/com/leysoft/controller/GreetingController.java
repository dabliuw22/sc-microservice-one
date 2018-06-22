package com.leysoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leysoft.service.inter.GreetingService;

@RestController
@RequestMapping(value = {"/greeting"})
public class GreetingController {
	
	@Autowired
	private GreetingService greetingService;
	
	@PostMapping
	public String greeting(String name) {
		return greetingService.greeting(name);
	}
}
