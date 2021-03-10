package com.gslab.assignment.EmployeeManagement.dao;

import com.gslab.assignment.EmployeeManagement.entities.Employee;
import com.gslab.assignment.EmployeeManagement.entities.EmployeeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeDao extends CrudRepository<Employee, Integer> {
    List<Employee> findAll();

    Optional<Object> findByIdEmpIdAndIdCompanyName(int employeeId, String companyName);

    void deleteByIdEmpIdAndIdCompanyName(int employeeId, String companyName);
}
