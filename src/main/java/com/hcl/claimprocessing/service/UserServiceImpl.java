package com.hcl.claimprocessing.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.claimprocessing.dto.UserRequestDto;
import com.hcl.claimprocessing.entity.User;
import com.hcl.claimprocessing.exception.LoginDeniedException;
import com.hcl.claimprocessing.exception.UserNotExistException;
import com.hcl.claimprocessing.exception.ValidInputException;
import com.hcl.claimprocessing.repository.UserRepository;
import com.hcl.claimprocessing.utils.ClaimConstants;

/**
 * This class will be used for the user login based on the Role
 * 
 * @author Subashri
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	/**
	 * @author Subashri
	 * This method will be used for the user login based on the Role
	 * 
	 * @param emailId , passcode
	 * @return This method returns message,userId,roleId
	 * @throws ValidInputException,INVALID_CREDENTIAL
	 * @exception It handles
	 *               ValidInputException,UserNotExistException,LoginDeniedException
	 */
	@Override
	public Optional<User> loginUser(UserRequestDto loginRequestDto) throws UserNotExistException, LoginDeniedException {

		logger.info(ClaimConstants.USER_LOGIN_SERVICE_INFO);

		Optional<User> user = userRepository.findByEmailIdAndPassCode(loginRequestDto.getEmailId(),
				loginRequestDto.getPassCode());
		if (!user.isPresent()) {
			throw new UserNotExistException(ClaimConstants.INVALID_CREDENTIAL);
		}
		if (!(user.get().getRoleId().equals(ClaimConstants.JUNIOR_APPROVER))
				&& !(user.get().getRoleId().equals(ClaimConstants.SENIOR_APPROVER))) {
			throw new LoginDeniedException(ClaimConstants.LOGIN_DENIED);
		}

		return user;
	}

}
