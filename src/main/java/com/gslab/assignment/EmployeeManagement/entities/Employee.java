package com.gslab.assignment.EmployeeManagement.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.*;

@Entity
@Table(name = "employee")
public class Employee {

    @EmbeddedId
    private EmployeeId id;

    @Column(name = "first_name", nullable = false)
    @NotEmpty(message = "Please enter firstName.")
    @Size(min = 2, message = "FirstName should have atleast 2 characters.")
    private String firstName;

    @Column(name = "middle_name", nullable = false)
    @NotEmpty(message = "Please enter middleName")
    @Size(min = 2, message = "MiddleName should have atleast 2 character.")
    private String middleName;

    @Column(name = "last_name", nullable = false)
    @NotEmpty(message = "Please enter lastName")
    @Size(min = 2, message = "LastName should have atleast 2 characters.")
    private String lastName;

    @Column(name = "date_of_birth", nullable = false)
    @Past
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "Please enter your dateOfBirth.")
    private Date dateOfBirth;

    @Column(name = "date_of_joining")
    @FutureOrPresent
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dateOfJoining;

    @ElementCollection
    @JoinTable(name = "employeeAddress")
    @JoinColumn(columnDefinition = "employeeId")
    @GenericGenerator(name = "increment-gen", strategy = "increment")
    @CollectionId(columns = {@Column(name = "addressId")}, type = @Type(type = "long"), generator = "increment-gen")
    @Size(min = 1, message = "Please enter atleast 1 address.")
    private List<Address> empAddress = new ArrayList<>();

    public Employee(String firstName, String middleName, String lastName, Date dateOfBirth, Date dateOfJoining, List<Address> empAddress
            , EmployeeId id) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.dateOfJoining = dateOfJoining;
        this.empAddress = empAddress;
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getDateOfJoining() {
        return dateOfJoining;
    }

    public void setDateOfJoining(Date dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public List<Address> getEmpAddress() {
        return empAddress;
    }

    public void setEmpAddress(List<Address> empAddress) {
        this.empAddress = empAddress;
    }


    public EmployeeId getId() {
        return id;
    }

    public void setId(EmployeeId id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Employee{" +
                ", id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", LastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", dateOfJoining=" + dateOfJoining +
                ", empAddress=" + empAddress +
                '}';
    }

    public Employee() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id.equals(employee.id) && firstName.equals(employee.firstName) && middleName.equals(employee.middleName) && lastName.equals(employee.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, middleName, lastName);
    }
}
