package com.leysoft.service.imple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.leysoft.service.inter.GreetingService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class GreetingServiceImp implements GreetingService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	private static final String GREETING_SERVICE_URL = "http://microservice-two/greeting";
	
	@HystrixCommand(fallbackMethod = "greetingFallback", 
			commandProperties = {
					@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "500"),
					@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
					@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "30"),
					@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
					@HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000"),
			})
	@Override
	public String greeting(String name) {
		return restTemplate.postForObject(GREETING_SERVICE_URL, name, String.class);
	}
	
	public String greetingFallback(String name) {
		return name + ", an error occurred";
	}
}
