package com.hcl.claimprocessing.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.claimprocessing.dto.CustomResponse;
import com.hcl.claimprocessing.entity.Policy;
import com.hcl.claimprocessing.exception.PolicyNotFoundException;
import com.hcl.claimprocessing.exception.ValidInputException;
import com.hcl.claimprocessing.service.PolicyService;
import com.hcl.claimprocessing.utils.ClaimConstants;

/**
 * This class is used to check the policy of users
 * 
 * @author Jyoshna
 *
 */
@RestController
@RequestMapping("/api/v1/policies")
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
public class PolicyController {

	private static final Logger logger = LoggerFactory.getLogger(PolicyController.class);

	@Autowired
	PolicyService policyService;

	/**
	 * @author Jyoshna
	 * This method is used to check whether the user has the policy inorder to claim
	 * 
	 * @param policyId
	 * @return This method returns the message of whether policy exists or not
	 * @throws ValidInputException
	 * @exception POLICY_ID_MANDATORY_EXCEPTION,POLICY_NOT_FOUND_EXCEPTION
	 * @throws PolicyNotFoundException
	 */

	@PostMapping("/")
	public ResponseEntity<CustomResponse> search(@RequestParam("policyId") Long policyId) {
		logger.info(ClaimConstants.SEARCH_POLICY_INFO_CONTROLLER);
		if (ObjectUtils.isEmpty(policyId)) {
			throw new PolicyNotFoundException(ClaimConstants.POLICY_NOT_EMPTY_EXCEPTION);
		}
		CustomResponse policyResponse = new CustomResponse();
		Optional<Policy> policyOptional = policyService.search(policyId);
		if (!policyOptional.isPresent()) {
			throw new PolicyNotFoundException(ClaimConstants.POLICY_NOT_FOUND_EXCEPTION);
		}
		policyResponse.setMessage(ClaimConstants.POLICY_ID_EXIST);
		policyResponse.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<>(policyResponse, HttpStatus.OK);
	}
}
