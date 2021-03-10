package com.gslab.assignment.EmployeeManagement.entities;

import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Embeddable
public class EmployeeId implements Serializable {
    @Column(nullable = false, name = "empId")
    private int empId;
    @Column(nullable = false, length = 500, name = "companyName")
    private String companyName;

    public EmployeeId(int empId, String companyName) {
        this.empId = empId;
        this.companyName = companyName;
    }

    public EmployeeId() {
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeId that = (EmployeeId) o;
        return empId == that.empId && companyName.equals(that.companyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empId, companyName);
    }

    @Override
    public String toString() {
        return "EmployeeId{" +
                "empId=" + empId +
                ", companyName='" + companyName + '\'' +
                '}';
    }
}
