package com.mindex.challenge.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;

@RestController
public class CompensationController {

	private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

	@Autowired
	private CompensationService compensationService;

	/**
	 * Post Compensation Object
	 * 
	 * @param Compensation object (JSON)
	 * @return Compensation data model
	 */
	@PostMapping("/compensation")
	public Compensation create(@RequestBody Compensation compensation) {
		LOG.debug("Received employee compensation create request for [{}]", compensation);

		return compensationService.create(compensation);
	}

	/**
	 * Get Compensation data model for specified employeeId
	 * 
	 * @param employeeId (passing from URL)
	 * @return
	 */
	@GetMapping("/compensation/{id}")
	public Compensation read(@PathVariable String id) {
		LOG.debug("Received employee compensation get request for id [{}]", id);

		return compensationService.read(id);
	}
}
