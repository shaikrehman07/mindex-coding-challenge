package com.mindex.challenge.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureServiceImplTest {
	
	private String reportStructureEmployeeIdUrl;
	
	@Autowired
    private EmployeeService employeeService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    
    @Before
    public void setup() {
        reportStructureEmployeeIdUrl = "http://localhost:" + port + "/reportStructure/{id}";
    }

	@Test
	public void testRead() {
		
		Employee testEmployee = employeeService.read("16a596ae-edd3-4847-99fe-c4518e82c86f");
		int expectedNumberofReports = 4;
		String expectedEmployeeFirstName = "John";
		
		//Read checks
		ReportingStructure reportingStructure = restTemplate.
												getForEntity(reportStructureEmployeeIdUrl, ReportingStructure.class, testEmployee.getEmployeeId()).
												getBody();
		assertNotNull(reportingStructure);
		assertTrue(expectedEmployeeFirstName.equals(reportingStructure.getEmployee().getFirstName()));
		assertEquals(expectedNumberofReports, reportingStructure.getNumberOfReports());
		
	}

}
