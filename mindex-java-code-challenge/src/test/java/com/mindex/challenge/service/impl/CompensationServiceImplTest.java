package com.mindex.challenge.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {

	private String compensationUrl;
	private String compensationEmployeeIdUrl;

	@Autowired
	private EmployeeService employeeService;

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Before
	public void setup() {
		compensationUrl = "http://localhost:" + port + "/compensation";
		compensationEmployeeIdUrl = "http://localhost:" + port + "/compensation/{id}";
	}

	@Test
	public void testCreateRead() {

		Employee testEmployee = employeeService.read("16a596ae-edd3-4847-99fe-c4518e82c86f");
		Compensation testCompensation = new Compensation();
		testCompensation.setEmployee(testEmployee);
		testCompensation.setSalary(100000);
		testCompensation.setEffectiveDate(new Date());
		
		//Create checks
		Compensation createdCompensation = restTemplate.postForEntity(compensationUrl, testCompensation, Compensation.class).getBody();
		assertNotNull(createdCompensation);
		assertCompensationEquivalence(testCompensation, createdCompensation);
		
		//Read checks
		Compensation readCompensation = restTemplate
				.getForEntity(compensationEmployeeIdUrl, Compensation.class, testEmployee.getEmployeeId())
				.getBody();
		assertCompensationEquivalence(createdCompensation, readCompensation);

	}
	
	private static void assertCompensationEquivalence(Compensation expected, Compensation actual) {
        assertEquals(expected.getEmployee().getEmployeeId(), actual.getEmployee().getEmployeeId());
        assertEquals(expected.getEmployee().getFirstName(), actual.getEmployee().getFirstName());
        assertEquals(expected.getSalary(), actual.getSalary());
        assertEquals(expected.getEffectiveDate(), actual.getEffectiveDate());
    }

}
