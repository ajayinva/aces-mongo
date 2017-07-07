/**
 * 
 */
package com.aces.aws.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;

import com.aces.aws.domain.EmailDto;

/**
 * @author aagarwal
 *
 */
public class EmailService {
	/**
	 * 
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
	/**
	 * 
	 */
	@Autowired
	private MailSender mailSender;
	/**
	 * 
	 */
	@Async
	public void sendEmail(EmailDto dto){
		LOGGER.debug("Sending an Email from EmailService");		
		mailSender.send(prepareSimpleMailMessage(dto));
	}
	/**
	 * 
	 * @param dto
	 * @return
	 */
	private SimpleMailMessage prepareSimpleMailMessage(EmailDto dto){
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(dto.getTo());
		message.setFrom(dto.getFrom());
		message.setText(dto.getMessage());
		message.setSubject(dto.getSubject());
		return message;
	}
}
