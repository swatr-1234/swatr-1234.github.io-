package com.gslab.assignment.EmployeeManagement.service;

import com.gslab.assignment.EmployeeManagement.entities.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> findAllEmployees();

    Employee findEmployeeByEmployeeId(int employeeId,String companyName);

    Employee createNewEmployee(Employee employee);

    Employee updateEmployee(Employee employee);

    void deleteEmployeeByEmployeeId(int employeeId,String companyName);
}
