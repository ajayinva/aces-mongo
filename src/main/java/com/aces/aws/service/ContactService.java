/**
 * 
 */
package com.aces.aws.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aces.aws.domain.ContactDto;
import com.aces.aws.domain.EmailDto;

/**
 * @author aagarwal
 *
 */
@Service
public class ContactService {
	/**
	 * 
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ContactService.class);
	/**
	 * 
	 */
	@Value("${contactto.address}")
	private String contactToAddress;
	/**
	 * 
	 */
	@Value("${contactfrom.address}")
	private String contactFromAddress;
	/**
	 * 
	 */
	@Autowired
	private EmailService emailService;
	/**
	 * 
	 * @param dto
	 */
	public void save(ContactDto contactDto){
		LOGGER.debug("Inside save() for ContactService");
		EmailDto emailDto = new EmailDto();
		emailDto.setFrom(contactFromAddress);
		emailDto.setMessage(contactDto.getMessage());
		emailDto.setSubject("Ref:"+contactDto.getName()+";Email:"+contactDto.getEmail());
		emailDto.setTo(contactToAddress);		 
		emailService.sendEmail(emailDto);		
	}
}
