package com.aces.aws.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aces.aws.entity.User;

/**
 * 
 * @author aagarwal
 *
 */
@RestController
public class LoginController extends BaseController  {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
	/**
	 * 
	 * @return
	 */
	@RequestMapping("/login")
	public Map<String,String> login(){
		LOGGER.debug("Inside Login Controller");
		Map<String,String> result = new HashMap<>();
		result.put("User,", "Ajay");
		return result;
	}
	/**
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/user")
	public Map<String, Object> user(Principal user) {
		Map<String, Object> userDetails = new HashMap<>();
		if(user!=null){			
			UsernamePasswordAuthenticationToken token =  (UsernamePasswordAuthenticationToken) user;
			User acesUser = (User)token.getPrincipal();
			userDetails.put("name",  acesUser.name);						
		}
		return userDetails;
	}
}
