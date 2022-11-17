package com.gslab.assignment.EmployeeManagement.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gslab.assignment.EmployeeManagement.dao.EmployeeDao;
import com.gslab.assignment.EmployeeManagement.entities.Address;
import com.gslab.assignment.EmployeeManagement.entities.Employee;
import com.gslab.assignment.EmployeeManagement.entities.EmployeeId;
import com.gslab.assignment.EmployeeManagement.service.EmployeeService;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    
    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private WebApplicationContext wc;

    ObjectMapper obj= new ObjectMapper();


    public static List<Address> l1= new ArrayList<>();

    public static Employee e= new Employee();

    public static List<Employee> empList = new ArrayList<>();


    @Before
    public void setUp(){

        mockMvc = MockMvcBuilders.webAppContextSetup(wc).build();
    }

 @Test
    void createNewEmployee() throws Exception {
        l1.add(new Address("THANE","800000","MAHARASHTRA","LANE1"));
        l1.add(new Address("PUNE","800000","MAHARASHTRA","LANE2"));

        e.setId(new EmployeeId(1,"GSLAB"));
        e.setFirstName("TOM");
        e.setMiddleName("DANIEL");
        e.setLastName("RADCLIFF");
        e.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2001-01-12 15:02:22"));
        e.setDateOfJoining(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2021-03-11 15:01:00"));
        e.setEmpAddress(l1);

        Mockito.when(employeeService.createNewEmployee(Mockito.any(Employee.class))).thenReturn(e);

        MvcResult result= mockMvc.perform(post("/employee-management/employee")
                         .contentType(MediaType.APPLICATION_JSON_VALUE)
                         .content(obj.writeValueAsString(e)))
                         .andExpect(status().isOk()).andReturn();
        Employee e1= obj.readValue(result.getResponse().getContentAsString(),Employee.class);
        assertThat(e).isEqualTo(e1);
    }

    @Test
    void getEmployeeByEmployeeId() throws Exception {

        Mockito.when(employeeService.findEmployeeByEmployeeId(Mockito.anyInt(),Mockito.anyString())).thenReturn(e);
        MvcResult result= mockMvc.perform(get("/employee-management/employee?employeeId=1&companyName=GSLAB")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        Employee e1= obj.readValue(result.getResponse().getContentAsString(),Employee.class);
        assertThat(e).isEqualTo(e1);
    }

    @Test
    void getAllEmployees() throws Exception {

        Employee e1= new Employee();

        e1.setId(new EmployeeId(2,"GSLAB"));
        e1.setFirstName("SWATI");
        e1.setMiddleName("RAVINDRA");
        e1.setLastName("JADHAV");
        e1.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2001-04-12 15:02:22"));
        e1.setDateOfJoining(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2021-03-11 15:01:00"));
        e1.setEmpAddress(l1);

        List<Employee> employeeList= new ArrayList<>();
        employeeList.add(e);
        employeeList.add(e1);

        Mockito.when(employeeService.findAllEmployees()).thenReturn(employeeList);

        MvcResult result= mockMvc.perform(get("/employee-management/employees")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();


        List<Employee> empList1= obj.readValue(result.getResponse().getContentAsString(),new TypeReference <List<Employee>>(){});
        assertThat(employeeList).isEqualTo(empList1);
        empList1.forEach(System.out::println);

    }

    @Test
    void deleteEmployeeByEmployeeId() throws Exception {

        Mockito.when(employeeService.findEmployeeByEmployeeId(Mockito.anyInt(),Mockito.anyString())).thenReturn(e);
        MvcResult result= mockMvc.perform(delete("/employee-management/employee?employeeId=1&companyName=GSLAB")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        Mockito.verify(employeeService,Mockito.times(1)).deleteEmployeeByEmployeeId(Mockito.anyInt(),Mockito.anyString());
    }

    @Test
    void updateEmployee() throws Exception {


        Mockito.when(employeeService.findEmployeeByEmployeeId(1,"GSLAB")).thenReturn(e);
        l1.add(new Address("SATARA","12367","MAHARASHTRA","LANE1"));
        e.setId(new EmployeeId(1,"GSLAB"));
        e.setFirstName("TINA");
        e.setMiddleName("MANI");
        e.setLastName("RAO");
        e.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2001-04-15 15:02:22"));
        e.setDateOfJoining(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2021-03-11 15:01:00"));
        e.setEmpAddress(l1);

        Mockito.when(employeeService.updateEmployee(e)).thenReturn(e);
        MvcResult result= mockMvc.perform(put("/employee-management/employee?employeeId=1&companyName=GSLAB")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(obj.writeValueAsString(e)))
                .andExpect(status().isAccepted()).andReturn();
        Employee e1= obj.readValue(result.getResponse().getContentAsString(),Employee.class);
        assertThat(e).isEqualTo(e1);
    }

}
