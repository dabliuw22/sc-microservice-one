
package com.leysoft.service.imple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.leysoft.dto.GreetingRequest;
import com.leysoft.dto.GreetingResponse;
import com.leysoft.service.inter.GreetingService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class GreetingServiceImp implements GreetingService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String GREETING_SERVICE_URL = "http://microservice-two/greeting";

    @HystrixCommand(
            fallbackMethod = "greetingFallback",
            commandKey = "GreetingService.greeting",
            commandProperties = {
                @HystrixProperty(
                        name = "execution.isolation.thread.timeoutInMilliseconds",
                        value = "500"),
                @HystrixProperty(
                        name = "circuitBreaker.requestVolumeThreshold",
                        value = "10"),
                @HystrixProperty(
                        name = "circuitBreaker.errorThresholdPercentage",
                        value = "30"),
                @HystrixProperty(
                        name = "circuitBreaker.sleepWindowInMilliseconds",
                        value = "10000"),
                @HystrixProperty(
                        name = "metrics.rollingStats.timeInMilliseconds",
                        value = "10000"),
            })
    @Override
    public GreetingResponse greeting(GreetingRequest request) {
        return restTemplate.postForObject(GREETING_SERVICE_URL, request, GreetingResponse.class);
    }

    public GreetingResponse greetingFallback(GreetingRequest request) {
        GreetingResponse response = new GreetingResponse();
        response.setMessage(request.getName() + ", an error occurred");
        return response;
    }
}
