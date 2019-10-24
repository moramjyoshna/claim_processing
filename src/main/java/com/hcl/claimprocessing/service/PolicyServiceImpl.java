package com.hcl.claimprocessing.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hcl.claimprocessing.entity.Policy;
import com.hcl.claimprocessing.exception.PolicyNotFoundException;
import com.hcl.claimprocessing.repository.PolicyRepository;
import com.hcl.claimprocessing.utils.ClaimConstants;

/**
 * This class is used to check the policy of users
 * 
 * @author Jyoshhna
 *
 */

@Service
public class PolicyServiceImpl implements PolicyService {

	@Autowired
	PolicyRepository policyRepository;
	private static final Logger logger = LoggerFactory.getLogger(PolicyServiceImpl.class);

	/**
	 * @author Jyoshhna
	 * This method is used to check whether the user has the policy inorder to claim
	 * 
	 * @param policyId
	 * @return This method returns the message of whether policy exists or not
	 * @exception POLICY_NOT_FOUND_EXCEPTION
	 * @throws PolicyNotFoundException
	 */

	@Override
	public Optional<Policy> search(Long policyId) {
		logger.info(ClaimConstants.SEARCH_POLICY_INFO_SERVICE);
		Optional<Policy> policy = policyRepository.findById(policyId);
		if (!policy.isPresent())
			throw new PolicyNotFoundException(ClaimConstants.POLICY_NOT_FOUND_EXCEPTION);
		return policy;
	}

}
