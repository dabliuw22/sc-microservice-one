package com.leysoft.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
@RequestMapping(value = {"/init"})
public class InitController {
	
	@Value(value = "${properties.example}")
	private String exampleProperty;
	
	@GetMapping
	public ResponseEntity<String> init() {
		return ResponseEntity.ok().body(exampleProperty);
	}
}
