package com.mindex.challenge.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.mindex.challenge.data.Compensation;

@Repository
public interface CompensationRepository extends MongoRepository<Compensation, String>{
	//Query by employeeId of employee atrribute in Compensation data model
	Compensation findByEmployee_EmployeeId(String employeeId);
}
