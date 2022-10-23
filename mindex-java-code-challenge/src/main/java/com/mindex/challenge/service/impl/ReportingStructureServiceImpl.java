package com.mindex.challenge.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {

	private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

	@Autowired
	private EmployeeService employeeService;

	/**
	 * Description: Method returns ReportingStructure Object with setting employee
	 * object and total no.of reports of that employee
	 * 
	 * @params employeeId (String)
	 * @return ReportingStructure data model
	 */
	@Override
	public ReportingStructure read(String id) {

		LOG.debug("Reading Input employee with id [{}]", id);

		Employee employee = employeeService.read(id);

		ReportingStructure reportStructure = new ReportingStructure();

		reportStructure.setEmployee(employee);
		reportStructure.setNumberOfReports(totalReportNumbers(id));

		return reportStructure;
	}

	/**
	 * @param employeeId
	 * @return Integer (Number of reporting employees of input employee)
	 */
	public int totalReportNumbers(String employeeId) {

		LOG.debug("Reading employee with id to get reports [{}]", employeeId);

		int totalNumberOfReports = 0;

		Employee employee = employeeService.read(employeeId);
		List<Employee> directReports = employee.getDirectReports();

		if (directReports != null) {
			for (Employee emp : directReports) {
				int reportingEmployeeReports = totalReportNumbers(emp.getEmployeeId());
				totalNumberOfReports += 1 + reportingEmployeeReports;
			}
		}

		return totalNumberOfReports;
	}

}
