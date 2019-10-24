package com.hcl.claimprocessing.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.claimprocessing.entity.Ailments;
import com.hcl.claimprocessing.exception.HospitalNotFoundException;
import com.hcl.claimprocessing.service.AilmentService;
import com.hcl.claimprocessing.utils.ClaimConstants;

/**
 * This class is used to get the list of ailments based on selected diagnosis
 * 
 * 
 * @author Jyoshna
 */
@RestController
@RequestMapping("/api/v1/ailments")
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
public class AilmentController {

	@Autowired
	AilmentService ailmentService;

	private static final Logger logger = LoggerFactory.getLogger(AilmentController.class);

	/**
	 * @author Jyoshna
	 * This method is used to get the list of ailments based on selected diagnosis .
	 * 
	 * @param diagnosisId
	 * @exception AILMENT_INFO_NOT_EXIST
	 * @return This method returns the list of ailments
	 * @throws HospitalNotFoundException
	 */

	@GetMapping("/{diagnosisId}")
	public ResponseEntity<List<Ailments>> getAilmentList(@PathVariable Integer diagnosisId)
			throws HospitalNotFoundException {
		logger.info(ClaimConstants.GET_AILMENT_LIST_CONTROLLER);
		Optional<List<Ailments>> ailmentListOptional = ailmentService.getAilmentList(diagnosisId);
		List<Ailments> ailmentList = new ArrayList<>();
		if (ailmentListOptional.isPresent()) {
			ailmentList = ailmentListOptional.get();
			if (ailmentList.isEmpty()) {
				throw new HospitalNotFoundException(ClaimConstants.AILMENT_INFO_NOT_EXIST);
			}
		}
		return new ResponseEntity<>(ailmentList, HttpStatus.OK);

	}

}
