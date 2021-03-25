package com.gslab.assignment.EmployeeManagement.service;

import com.gslab.assignment.EmployeeManagement.dao.EmployeeDao;
import com.gslab.assignment.EmployeeManagement.entities.Address;
import com.gslab.assignment.EmployeeManagement.entities.Employee;
import com.gslab.assignment.EmployeeManagement.entities.EmployeeId;
import com.gslab.assignment.EmployeeManagement.serviceImpl.EmployeeServiceImpl;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static java.util.Optional.ofNullable;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeDao employeeDao;

    @InjectMocks
    private EmployeeServiceImpl employeeServiceImpl;

    private AutoCloseable closeable;

    List<Address> l1 = new ArrayList<>();
    Employee e = new Employee();
    Employee e2 = new Employee();

    @BeforeEach
    public void setUp( TestInfo testInfo ) throws Exception {

        closeable = MockitoAnnotations.openMocks( this );
        System.out.println( "Start...." + testInfo.getDisplayName() );
        l1.add( new Address( "THANE", "800000", "MAHARASHTRA", "LANE1" ) );
        l1.add( new Address( "PUNE", "800000", "MAHARASHTRA", "LANE2" ) );

        e.setId( new EmployeeId( 111, "GSLAB" ) );
        e.setFirstName( "TOM" );
        e.setMiddleName( "DANIEL" );
        e.setLastName( "RADCLIFF" );
        e.setDateOfBirth( new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" ).parse( "2001-01-12 15:02:22" ) );
        e.setDateOfJoining( new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" ).parse( "2021-03-29 15:01:00" ) );
        e.setEmpAddress( l1 );

        e2.setId( new EmployeeId( 2, "GSLAB" ) );
        e2.setFirstName( "SWATI" );
        e2.setMiddleName( "RAVINDRA" );
        e2.setLastName( "JADHAV" );
        e2.setDateOfBirth( new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" ).parse( "2001-04-12 15:02:22" ) );
        e2.setDateOfJoining( new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" ).parse( "2021-03-29 15:01:00" ) );
        e2.setEmpAddress( l1 );

    }

    @AfterEach
    public void tearDown( TestInfo testInfo ) throws Exception {

        closeable.close();
        System.out.println( "End...." + testInfo.getDisplayName() );

    }

    @Test
    @DisplayName("createNewEmployee method")
    @Order(1)
    void createNewEmployee() {

        assumeThat( e ).isNotNull();
        when( employeeDao.save( e ) ).thenReturn( e );
        assertThat( employeeServiceImpl.createNewEmployee( e ) ).isEqualTo( e );

    }

    @Test
    @DisplayName("getEmployeeByEmployeeId method")
    @Order(2)
    void getEmployeeByEmployeeId() {

        given( employeeDao.findByIdEmpIdAndIdCompanyName( 111, "GSLAB" ) ).willReturn( ofNullable( e ) );
        Employee e1 = employeeServiceImpl.findEmployeeByEmployeeId( 111, "GSLAB" );
        Assertions.assertNotNull( e1 );
        assertThat( e1 ).isEqualTo( e );

    }

    @Test
    @DisplayName("getAllEmployees method")
    @Order(3)
    void getAllEmployees() {

        List<Employee> employeeList = new ArrayList<>();
        employeeList.add( e );
        employeeList.add( e2 );

        when( employeeDao.findAll() ).thenReturn( employeeList );
        assertThat( employeeServiceImpl.findAllEmployees() ).isEqualTo( employeeList );

    }

    @Test
    @DisplayName("updateEmployee method")
    @Order(4)
    void updateEmployee() throws Exception {

        given( employeeDao.findByIdEmpIdAndIdCompanyName( 111, "GSLAB" ) ).willReturn( ofNullable( e ) );
        l1.add( new Address( "SATARA", "12367", "MAHARASHTRA", "LANE1" ) );
        e.setId( new EmployeeId( 111, "GSLAB" ) );
        e.setFirstName( "TINA" );
        e.setMiddleName( "MANI" );
        e.setLastName( "RAO" );
        e.setDateOfBirth( new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" ).parse( "2001-04-15 15:02:22" ) );
        e.setDateOfJoining( new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" ).parse( "2021-03-30 15:01:00" ) );
        e.setEmpAddress( l1 );
        when( employeeDao.save( e ) ).thenReturn( e );
        assertThat( employeeServiceImpl.updateEmployee( e ) ).isEqualTo( e );

    }

    @Test
    @DisplayName("deleteEmployeeByEmployeeId method")
    @Order(5)
    void deleteEmployeeByEmployeeId() {

        when( employeeDao.findByIdEmpIdAndIdCompanyName( 111, "GSLAB" ) ).thenReturn( ofNullable( e ) );
        Employee e1 = employeeServiceImpl.findEmployeeByEmployeeId( 111, "GSLAB" );
        doNothing().when( employeeDao ).deleteByIdEmpIdAndIdCompanyName( 111, "GSLAB" );
        assertThat( e1 ).isEqualTo( e );

    }
}
