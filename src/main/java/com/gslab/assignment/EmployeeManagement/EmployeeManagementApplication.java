package com.gslab.assignment.EmployeeManagement;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.gslab.assignment.EmployeeManagement","com.gslab.assignment.EmployeeManagement.contoller"})
@OpenAPIDefinition
public class EmployeeManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeManagementApplication.class, args);
		System.out.print("------welcome--to---EmployeeManagementProject-");
	}


}
