package com.gslab.assignment.EmployeeManagement.serviceImpl;

import com.gslab.assignment.EmployeeManagement.dao.EmployeeDao;
import com.gslab.assignment.EmployeeManagement.entities.Employee;
import com.gslab.assignment.EmployeeManagement.exception.ErrorMessages;
import com.gslab.assignment.EmployeeManagement.exception.ResourceNotFoundException;
import com.gslab.assignment.EmployeeManagement.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    @Autowired
    EmployeeDao employeeDao;

    @Override
    public List<Employee> findAllEmployees() {
        log.info("Inside EmployeeServiceImpl- findAllEmployees method");
        return employeeDao.findAll();

    }

    @Override
    public Employee findEmployeeByEmployeeId(int employeeId, String companyName) {
        log.info("Inside EmployeeServiceImpl findEmployeeByEmployeeId method");
        return (Employee) employeeDao.findByIdEmpIdAndIdCompanyName(employeeId, companyName).orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.RESOURCE_NOT_FOUND_EXCEPTION + ": " + employeeId));
    }

    @Override
    public Employee createNewEmployee(Employee employee) {
        log.info("Inside EmployeeServiceImpl- createNewEmployee method");
        return employeeDao.save(employee);
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        log.info("Inside EmployeeServiceImpl- updateEmployee method");
        return employeeDao.save(employee);
    }

    @Override
    public void deleteEmployeeByEmployeeId(int employeeId, String companyName) {
        log.info("Inside EmployeeServiceImpl- deleteEmployeeByEmployeeId method");
        employeeDao.deleteByIdEmpIdAndIdCompanyName(employeeId, companyName);
    }
}