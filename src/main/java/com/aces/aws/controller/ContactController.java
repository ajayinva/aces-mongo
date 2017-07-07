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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.aces.aws.domain.ContactDto;
import com.aces.aws.service.ContactService;
/**
 * @author aagarwal
 *
 */
@RestController
public class ContactController  extends BaseController {
	/**
	 * 
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ContactController.class);
	/**
	 * 
	 */
	@Autowired
	private ContactService contactService;
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/contact", method = RequestMethod.POST)
	public Map<String,Object> save(
		@RequestBody 
		@Valid
		ContactDto dto
	){
		LOGGER.debug("Inside save() for ContactController");
		Map<String,Object> result = new HashMap<>();
		contactService.save(dto);
		result.put("message", "We have recieved you message and will be in touch shortly.");
		return result;
	}
}
