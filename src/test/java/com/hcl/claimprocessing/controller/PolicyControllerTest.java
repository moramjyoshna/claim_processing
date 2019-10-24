package com.hcl.claimprocessing.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.hcl.claimprocessing.dto.CustomResponse;
import com.hcl.claimprocessing.entity.Policy;
import com.hcl.claimprocessing.exception.PolicyNotFoundException;
import com.hcl.claimprocessing.exception.ValidInputException;
import com.hcl.claimprocessing.service.PolicyServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class PolicyControllerTest {
	@Mock
	PolicyServiceImpl policyService;
	@InjectMocks
	PolicyController policyController;
	Long policyId;
	Policy policy = new Policy();
	Optional<Policy> policyInfo;
	BindingResult bindingResult;
	FieldError fieldError;

	@Before
	public void initData() {
		bindingResult = mock(BindingResult.class);
		fieldError = new FieldError("userDto", "emailId", "must be valid");
		policyId = 123456789L;
		policy.setAilmentId(1);
		policy.setPolicyId(123456789L);
		policy.setUserId(2);
	}

	@Test(expected = PolicyNotFoundException.class)
	public void testSearchPolicyNull() throws ValidInputException {
		policyId = null;
		ResponseEntity<CustomResponse> response = policyController.search(policyId);
		assertNotNull(response);
	}

	@Test
	public void testSearch() throws ValidInputException {
		policyInfo = Optional.of(policy);
		Mockito.when(policyService.search(Mockito.anyLong())).thenReturn(policyInfo);
		ResponseEntity<CustomResponse> response = policyController.search(policyId);
		assertNotNull(response);
		assertEquals(200, response.getStatusCode().value());
	}

	@Test
	public void testNegativeSearch() throws ValidInputException {
		ObjectUtils.isEmpty(policyId);
		policyInfo = Optional.of(policy);
		Mockito.when(policyService.search(Mockito.anyLong())).thenReturn(policyInfo);
		ResponseEntity<CustomResponse> response = policyController.search(policyId);
		assertNotNull(response);
		assertEquals(200, response.getStatusCode().value());
	}

	@Test(expected = PolicyNotFoundException.class)
	public void testNegativeEmptySearch() throws ValidInputException {
		Long policyId = null;
		ObjectUtils.isEmpty(policyId);
		policyInfo = Optional.of(policy);
		ResponseEntity<CustomResponse> response = policyController.search(policyId);
		assertNotNull(response);
		assertEquals(200, response.getStatusCode().value());
	}

	@Test(expected = PolicyNotFoundException.class)
	public void testSearchNull() throws ValidInputException {
		policyInfo = Optional.ofNullable(null);
		Mockito.when(policyService.search(Mockito.anyLong())).thenReturn(policyInfo);
		ResponseEntity<CustomResponse> response = policyController.search(policyId);
		assertNotNull(response);
		assertEquals(200, response.getStatusCode().value());
	}

}
