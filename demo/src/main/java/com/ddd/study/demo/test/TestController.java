package com.ddd.study.demo.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@RestController
public class TestController {

	@Operation(summary = "test api", description = "This is test api")
	@ApiResponses(@ApiResponse(code = 200, message = "정상 응답", response = String.class))
	@GetMapping("/api/test")
	public String hello() {
		return "HELLO";
	}
}
