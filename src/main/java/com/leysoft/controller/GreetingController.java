
package com.leysoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leysoft.client.inter.MicroserviceTwoClient;
import com.leysoft.dto.GreetingRequest;
import com.leysoft.dto.GreetingResponse;
import com.leysoft.dto.MessageRequest;
import com.leysoft.dto.MessageResponse;
import com.leysoft.service.inter.GreetingService;
import com.leysoft.service.inter.SenderService;

@RestController
@RequestMapping(
        value = {
            "/greeting"
        })
public class GreetingController {

    @Autowired
    private MicroserviceTwoClient feignClient;

    @Autowired
    private GreetingService greetingService;

    @Autowired
    private SenderService senderService;

    @GetMapping(
            value = {
                "/{name}"
            })
    public GreetingResponse greeting(@PathVariable(
            name = "name") String name) {
        GreetingRequest request = new GreetingRequest();
        request.setName(name);
        return greetingService.greeting(request);
    }

    @GetMapping(
            value = {
                "/feign/{name}"
            })
    public GreetingResponse greetingFeing(@PathVariable(
            name = "name") String name) {
        GreetingRequest request = new GreetingRequest();
        request.setName(name);
        return feignClient.greeting(request);
    }

    @PostMapping
    public MessageResponse message(@RequestBody MessageRequest request) {
        boolean isSender = senderService.send(request);
        MessageResponse response = new MessageResponse();
        String message =
                isSender ? request.getMessage() + " exitoso" : request.getMessage() + " fallido";
        response.setMessage(message);
        return response;
    }
}
