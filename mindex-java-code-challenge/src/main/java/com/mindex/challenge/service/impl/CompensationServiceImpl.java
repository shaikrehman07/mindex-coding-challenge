package com.mindex.challenge.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;

@Service
public class CompensationServiceImpl implements CompensationService {

	private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	@Autowired
	private CompensationRepository compensationRepository;

	@Autowired
	private EmployeeService employeeService;

	/**
	 * Description: Method returns Compensation object with full employee details
	 * and inserting data into MongoRepository
	 *
	 * @param Compensation Object
	 * @return Compensation data model
	 */
	@Override
	public Compensation create(Compensation compensation) {
		LOG.debug("Creating compensation for employee [{}]", compensation);

		Employee employee = employeeService.read(compensation.getEmployee().getEmployeeId());

		compensation.setEmployee(employee);
		compensationRepository.insert(compensation);

		return compensation;
	}

	/**
	 * Description: Returns Compensation details of input employeeId
	 *
	 * @param employeeId
	 * @return Compensation data model
	 */
	@Override
	public Compensation read(String id) {

		Compensation compensation = compensationRepository.findByEmployee_EmployeeId(id);

		if (compensation == null) {
			throw new RuntimeException("Compensation is not found for employeeId: " + id);
		}

		return compensation;

	}

}
