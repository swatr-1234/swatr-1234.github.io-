package com.gslab.assignment.EmployeeManagement.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gslab.assignment.EmployeeManagement.controller.EmployeeController;
import com.gslab.assignment.EmployeeManagement.dao.EmployeeDao;
import com.gslab.assignment.EmployeeManagement.entities.Address;
import com.gslab.assignment.EmployeeManagement.entities.Employee;
import com.gslab.assignment.EmployeeManagement.entities.EmployeeId;
import com.gslab.assignment.EmployeeManagement.exception.ResourceNotFoundException;
import com.gslab.assignment.EmployeeManagement.serviceImpl.EmployeeServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeServiceImpl.class)
public class EmployeeServiceTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeDao employeeDao;

    @Autowired
    private EmployeeServiceImpl employeeServiceImpl;

    @Autowired
    private WebApplicationContext wc;

    ObjectMapper obj = new ObjectMapper();


    public static List<Address> l1 = new ArrayList<>();

    public static Employee e = new Employee();

    public static List<Employee> empList = new ArrayList<>();


    @Before
    public void setUp() {

        mockMvc = MockMvcBuilders.webAppContextSetup(wc).build();
    }

    @Test
    void createNewEmployee() throws Exception {
        l1.add(new Address("THANE", "800000", "MAHARASHTRA", "LANE1"));
        l1.add(new Address("PUNE", "800000", "MAHARASHTRA", "LANE2"));

        e.setId(new EmployeeId(111, "GSLAB"));
        e.setFirstName("TOM");
        e.setMiddleName("DANIEL");
        e.setLastName("RADCLIFF");
        e.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2001-01-12 15:02:22"));
        e.setDateOfJoining(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2021-03-11 15:01:00"));
        e.setEmpAddress(l1);

        Mockito.when(employeeDao.save(e)).thenReturn(e);

        assertThat(employeeServiceImpl.createNewEmployee(e)).isEqualTo(e);
    }

    @Test
    void getEmployeeByEmployeeId() throws Exception {

        Employee e2 = new Employee();
        List<Address> addressList = new ArrayList<>();
        l1.add(new Address("PUNE", "800000", "MAHARASHTRA", "LANE28"));

        e2.setId(new EmployeeId(112, "GSLAB"));
        e2.setFirstName("TOM");
        e2.setMiddleName("DANIEL");
        e2.setLastName("RADCLIFF");
        e2.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2001-01-12 15:02:22"));
        e2.setDateOfJoining(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2021-03-11 15:01:00"));
        e2.setEmpAddress(l1);

        Mockito.when(employeeDao.findByIdEmpIdAndIdCompanyName(112, "GSLAB")).thenReturn(java.util.Optional.ofNullable(e2));
    }

    @Test
    void getAllEmployees() throws Exception {

        Employee e1 = new Employee();

        e1.setId(new EmployeeId(2, "GSLAB"));
        e1.setFirstName("SWATI");
        e1.setMiddleName("RAVINDRA");
        e1.setLastName("JADHAV");
        e1.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2001-04-12 15:02:22"));
        e1.setDateOfJoining(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2021-03-11 15:01:00"));
        e1.setEmpAddress(l1);

        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(e);
        employeeList.add(e1);

        Mockito.when(employeeDao.findAll()).thenReturn(employeeList);
        assertThat(employeeServiceImpl.findAllEmployees()).isEqualTo(employeeList);


    }

    @Test
    void deleteEmployeeByEmployeeId(){

        Mockito.when(employeeDao.findByIdEmpIdAndIdCompanyName(111, "GSLAB")).thenReturn(java.util.Optional.ofNullable(e));
        Mockito.when(employeeDao.existsById(new EmployeeId(111, "GSLAB"))).thenReturn(false);
        assertFalse(employeeDao.existsById(new EmployeeId(111, "GSLAB")));
    }

    @Test
    void updateEmployee() throws Exception {

        Mockito.when(employeeDao.findByIdEmpIdAndIdCompanyName(1, "GSLAB")).thenReturn(java.util.Optional.ofNullable(e));
        l1.add(new Address("SATARA", "12367", "MAHARASHTRA", "LANE1"));
        e.setId(new EmployeeId(1, "GSLAB"));
        e.setFirstName("TINA");
        e.setMiddleName("MANI");
        e.setLastName("RAO");
        e.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2001-04-15 15:02:22"));
        e.setDateOfJoining(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2021-03-11 15:01:00"));
        e.setEmpAddress(l1);
        Mockito.when(employeeDao.save(e)).thenReturn(e);
        assertThat(employeeServiceImpl.updateEmployee(e)).isEqualTo(e);
    }
}
