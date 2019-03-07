
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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api
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
    @ApiOperation(
            value = "Greeting Operation",
            nickname = "greeting",
            httpMethod = "GET")
    @ApiResponses(
            value = {
                @ApiResponse(
                        code = 200,
                        message = "Success")
            })
    public GreetingResponse greeting(@ApiParam(
            name = "name",
            required = true,
            type = "String",
            example = "MyName") @PathVariable(
                    name = "name") String name) {
        GreetingRequest request = new GreetingRequest();
        request.setName(name);
        return greetingService.greeting(request);
    }

    @GetMapping(
            value = {
                "/feign/{name}"
            })
    @ApiOperation(
            value = "Greeting Feign Operation",
            nickname = "greeting-feign",
            httpMethod = "GET")
    @ApiResponses(
            value = {
                @ApiResponse(
                        code = 200,
                        message = "Success")
            })
    public GreetingResponse greetingFeign(@ApiParam(
            name = "name",
            required = true,
            type = "String",
            example = "MyName") @PathVariable(
                    name = "name") String name) {
        GreetingRequest request = new GreetingRequest();
        request.setName(name);
        return feignClient.greeting(request);
    }

    @PostMapping
    @ApiOperation(
            value = "Message Operation",
            nickname = "message",
            httpMethod = "POST")
    @ApiResponses(
            value = {
                @ApiResponse(
                        code = 200,
                        message = "Success")
            })
    public MessageResponse message(@ApiParam(
            name = "request",
            required = true,
            type = "MessageRequest") @RequestBody MessageRequest request) {
        boolean isSender = senderService.sendCustomMessageSource(request);
        MessageResponse response = new MessageResponse();
        String message =
                isSender ? request.getMessage() + " exitoso" : request.getMessage() + " fallido";
        response.setMessage(message);
        return response;
    }
}
