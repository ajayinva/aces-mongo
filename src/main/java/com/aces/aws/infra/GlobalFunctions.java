/**
 * 
 */
package com.aces.aws.infra;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.aces.aws.entity.User;

/**
 * @author aagarwal
 *
 */
public class GlobalFunctions {
	/**
	 * 
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalFunctions.class);
	/**
	 * 
	 * @param src
	 * @param dest
	 */
	public static void copyProperties(Object src, Object dest){				
		try{
			BeanUtils.copyProperties(dest, src);
		}catch(Exception e){
			LOGGER.debug("GlobalFunctions.copyProperties() failed: ", e);
		}
	}
	/**
	 * 
	 * @return
	 */
	public static User getCurrentUser(){
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();		
		return (User) authentication.getPrincipal();
	}
}
