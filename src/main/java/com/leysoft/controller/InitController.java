
package com.leysoft.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api
@RefreshScope
@RestController
@RequestMapping(
        value = {
            "/init"
        })
public class InitController {

    @Value(
            value = "${properties.example}")
    private String exampleProperty;

    @GetMapping
    @ApiOperation(
            value = "Init Operation",
            nickname = "init",
            httpMethod = "GET")
    @ApiResponses(
            value = {
                @ApiResponse(
                        code = 200,
                        message = "Success"),
                @ApiResponse(
                        code = 500,
                        message = "Server Error")
            })
    public ResponseEntity<String> init() {
        return ResponseEntity.ok().body(exampleProperty);
    }
}
