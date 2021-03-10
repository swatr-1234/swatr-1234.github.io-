package com.gslab.assignment.EmployeeManagement.controller;

import com.gslab.assignment.EmployeeManagement.entities.*;
import com.gslab.assignment.EmployeeManagement.exception.ErrorMessages;
import com.gslab.assignment.EmployeeManagement.exception.IdentityChangeException;
import com.gslab.assignment.EmployeeManagement.exception.ResourceNotFoundException;
import com.gslab.assignment.EmployeeManagement.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@Validated
@RestController
@RequestMapping("/employee-management")
public class EmployeeController {

    private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);
    @Autowired
    EmployeeService employeeService;


    @GetMapping(value = "/employees", produces = "application/json")
    public ResponseEntity getAllEmployees(HttpServletRequest request) {

        log.info("Inside Controller getAllEmployees method");
        List<Employee> employeeList = employeeService.findAllEmployees();

        if (employeeList.size() == 0) {
            throw new ResourceNotFoundException(ErrorMessages.RESOURCE_NOT_FOUND_EXCEPTION);
        } else {
            log.debug("Number of Employees are " + employeeList.size());
            return new ResponseEntity(employeeList, HttpStatus.OK);

        }

    }

    @GetMapping(value = "/employee")
    public Employee getEmployeeByEmployeeId(@RequestParam(name = "employeeId", required = true) int employeeId,
                                            @RequestParam(name = "companyName", required = true) String companyName) {
        log.info("Inside Controller getEmployeeByEmployeeId method");
        return employeeService.findEmployeeByEmployeeId(employeeId, companyName);
    }

    @PostMapping(value = "/employee")
    public Employee createNewEmployee(@Valid @RequestBody Employee employee) {
        log.info("Inside Controller createNewEmployee method");
        return employeeService.createNewEmployee(employee);

    }

    @PutMapping(value = "/employee", produces = "application/json")
    public ResponseEntity<Employee> updateEmployee(@RequestParam(name = "employeeId", required = true) int employeeId,
                                                   @RequestParam(name = "companyName", required = true) String companyName,
                                                   @Valid @RequestBody Employee employee, HttpServletRequest request) {
        log.info("Inside Controller updateEmployee method");

        Employee existingEmp = employeeService.findEmployeeByEmployeeId(employeeId, companyName);

        if (existingEmp.getId().getEmpId() != employee.getId().getEmpId()
                || !existingEmp.getId().getCompanyName().equalsIgnoreCase(employee.getId().getCompanyName())) {

            throw new IdentityChangeException(ErrorMessages.IDENTITY_CHANGE_EXCEPTION + ": " + employeeId);
        } else {
            existingEmp.setFirstName(employee.getFirstName());
            existingEmp.setMiddleName(employee.getMiddleName());
            existingEmp.setLastName(employee.getLastName());
            existingEmp.setDateOfBirth(employee.getDateOfBirth());
            existingEmp.setDateOfJoining(employee.getDateOfJoining());
            existingEmp.setEmpAddress(employee.getEmpAddress());
            employeeService.updateEmployee(existingEmp);
            return new ResponseEntity(existingEmp, HttpStatus.ACCEPTED);
        }
    }

    @DeleteMapping(value = "/employee")
    public ResponseEntity<Employee> deleteEmployeeByEmployeeId(@RequestParam(name = "employeeId", required = true) int employeeId,
                                                               @RequestParam(name = "companyName", required = true) String companyName) {
        log.info("Inside Controller deleteEmployeeByEmployeeId method");
        Employee existingEmp = employeeService.findEmployeeByEmployeeId(employeeId, companyName);
        employeeService.deleteEmployeeByEmployeeId(employeeId, companyName);
        return new ResponseEntity(HttpStatus.OK);
    }

}
