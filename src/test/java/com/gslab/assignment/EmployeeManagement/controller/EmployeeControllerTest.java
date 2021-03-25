package com.gslab.assignment.EmployeeManagement.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gslab.assignment.EmployeeManagement.entities.Address;
import com.gslab.assignment.EmployeeManagement.entities.Employee;
import com.gslab.assignment.EmployeeManagement.entities.EmployeeId;
import com.gslab.assignment.EmployeeManagement.service.EmployeeService;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private WebApplicationContext wc;

    ObjectMapper obj = new ObjectMapper();

    List<Address> l1 = new ArrayList<>();
    Employee e = new Employee();
    Employee e1 = new Employee();

    @BeforeEach
    public void setUp( TestInfo testInfo ) throws Exception {

        System.out.println( "Start...." + testInfo.getDisplayName() );
        mockMvc = MockMvcBuilders.webAppContextSetup( wc ).build();

        l1.add( new Address( "THANE", "800000", "MAHARASHTRA", "LANE1" ) );
        l1.add( new Address( "PUNE", "800000", "MAHARASHTRA", "LANE2" ) );

        e.setId( new EmployeeId( 1, "GSLAB" ) );
        e.setFirstName( "TOM" );
        e.setMiddleName( "DANIEL" );
        e.setLastName( "RADCLIFF" );
        e.setDateOfBirth( new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" ).parse( "2001-01-12 15:02:22" ) );
        e.setDateOfJoining( new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" ).parse( "2021-03-29 15:01:00" ) );
        e.setEmpAddress( l1 );

        e1.setId( new EmployeeId( 2, "GSLAB" ) );
        e1.setFirstName( "SWATI" );
        e1.setMiddleName( "RAVINDRA" );
        e1.setLastName( "JADHAV" );
        e1.setDateOfBirth( new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" ).parse( "2001-04-12 15:02:22" ) );
        e1.setDateOfJoining( new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" ).parse( "2021-03-29 15:01:00" ) );
        e1.setEmpAddress( l1 );

    }

    @AfterEach
    public void tearDown( TestInfo testInfo ) {

        System.out.println( "End...." + testInfo.getDisplayName() );

    }

    @Test
    @DisplayName("createNewEmployee method")
    @Order(1)
    void createNewEmployee() throws Exception {

        given( employeeService.createNewEmployee( Mockito.any( Employee.class ) ) ).willReturn( e );

        MvcResult result =mockMvc.perform( post( "/employee-management/employee" )
                .contentType( MediaType.APPLICATION_JSON_VALUE )
                .content( obj.writeValueAsString( e ) ) )
                .andExpect( status().isOk() )
                .andDo( print() )
                .andExpect( jsonPath( "$.id" ).value( new EmployeeId( 1, "GSLAB" ) ) )
                .andExpect( jsonPath( "$.firstName" ).value( "TOM" ) )
                .andExpect( jsonPath( "$.middleName" ).value( "DANIEL" ) )
                .andExpect( jsonPath( "$.lastName" ).value( "RADCLIFF" ) )
                .andReturn();

        Employee e1 = obj.readValue( result.getResponse().getContentAsString(), Employee.class );
        assertThat( e ).isEqualTo( e1 );

    }

    @Test
    @DisplayName("getEmployeeByEmployeeId method")
    @Order(2)
    void getEmployeeByEmployeeId() throws Exception {

        given( employeeService.findEmployeeByEmployeeId( Mockito.anyInt(), Mockito.anyString() ) )
                .willReturn( e );

        MvcResult result = mockMvc.perform( get( "/employee-management/employee?employeeId=1&companyName=GSLAB" )
                .contentType( MediaType.APPLICATION_JSON_VALUE ) )
                .andExpect( status().isOk() )
                .andDo( print() )
                .andExpect( jsonPath( "$.id" ).value( new EmployeeId( 1, "GSLAB" ) ) )
                .andExpect( jsonPath( "$.firstName" ).value( "TOM" ) )
                .andExpect( jsonPath( "$.middleName" ).value( "DANIEL" ) )
                .andExpect( jsonPath( "$.lastName" ).value( "RADCLIFF" ) )
                .andReturn();

        Employee e1 = obj.readValue( result.getResponse().getContentAsString(), Employee.class );
        assertThat( e ).isEqualTo( e1 );

    }

    @Test
    @DisplayName("getAllEmployees method")
    @Order(3)
    void getAllEmployees() throws Exception {

        List<Employee> employeeList = new ArrayList<>();
        employeeList.add( e );
        employeeList.add( e1 );

        given( employeeService.findAllEmployees() ).willReturn( employeeList );

        MvcResult result = mockMvc.perform( get( "/employee-management/employees" )
                .contentType( MediaType.APPLICATION_JSON_VALUE ) )
                .andExpect( status().isOk() ).andReturn();

        List<Employee> empList1 = obj.readValue( result.getResponse().getContentAsString(), new TypeReference<List<Employee>>() {
        } );
        assertThat( employeeList ).isEqualTo( empList1 );
        empList1.forEach( System.out::println );

    }

    @Test
    @DisplayName("deleteEmployeeByEmployeeId method")
    @Order(5)
    void deleteEmployeeByEmployeeId() throws Exception {

        given( employeeService.findEmployeeByEmployeeId( Mockito.anyInt(), Mockito.anyString() ) ).willReturn( e );
        MvcResult result = mockMvc.perform( delete( "/employee-management/employee?employeeId=1&companyName=GSLAB" )
                .contentType( MediaType.APPLICATION_JSON_VALUE ) )
                .andExpect( status().isOk() ).andReturn();
        Mockito.verify( employeeService, Mockito.times( 1 ) ).deleteEmployeeByEmployeeId( Mockito.anyInt(), Mockito.anyString() );
    }

    @Test
    @DisplayName("updateEmployee method")
    @Order(4)
    void updateEmployee() throws Exception {

        given( employeeService.findEmployeeByEmployeeId( 1, "GSLAB" ) ).willReturn( e );
        l1.add( new Address( "SATARA", "12367", "MAHARASHTRA", "LANE1" ) );
        e.setId( new EmployeeId( 1, "GSLAB" ) );
        e.setFirstName( "TINA" );
        e.setMiddleName( "MANI" );
        e.setLastName( "RAO" );
        e.setDateOfBirth( new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" ).parse( "2001-04-15 15:02:22" ) );
        e.setDateOfJoining( new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" ).parse( "2021-03-29 15:01:00" ) );
        e.setEmpAddress( l1 );

        Mockito.when( employeeService.updateEmployee( e ) ).thenReturn( e );
        MvcResult result = mockMvc.perform( put( "/employee-management/employee?employeeId=1&companyName=GSLAB" )
                .contentType( MediaType.APPLICATION_JSON_VALUE )
                .content( obj.writeValueAsString( e ) ) )
                .andExpect( status().isAccepted() ).andReturn();
        Employee e1 = obj.readValue( result.getResponse().getContentAsString(), Employee.class );
        assertThat( e ).isEqualTo( e1 );

    }

}