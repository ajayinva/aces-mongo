/**
 * 
 */
package com.aces.aws.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.aces.aws.domain.ChangePasswordDto;
import com.aces.aws.entity.User;
import com.aces.aws.infra.GlobalFunctions;
import com.aces.aws.service.UserService;
/**
 * @author aagarwal
 *
 */
@RestController
public class ChangePasswordController  extends BaseController {
	/**
	 * 
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ChangePasswordController.class);
	
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
       	
    @Autowired
    private UserService userService;
    /**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/changePassword", method = RequestMethod.GET)
	public Map<String,Object> changePasswordInit(){
		LOGGER.debug("Inside changePassword() for ChangePasswordController");		
		User user = GlobalFunctions.getCurrentUser();
		Map<String,Object> result = new HashMap<>();
		result.put("changePasswordDto", new ChangePasswordDto());
		result.put("name", user.name);
		result.put("email", user.userName);
		return result;
	}
    /**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public Map<String,Object> changePasswordSave(
		@RequestBody 
		@Valid
		ChangePasswordDto dto
	){
		LOGGER.debug("Inside changePassword() for ChangePasswordController");
		Map<String,Object> result = new HashMap<>();		
		User user = GlobalFunctions.getCurrentUser();
		if(passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword())){			
			userService.updatePassword(user.userName, dto.getPassword());
			result.put("success", true);
			result.put("message", "Your password has been changed");
		} else {
			result.put("error", true);
			result.put("message", "Invalid current password");
		}
		return result;
	}
}
