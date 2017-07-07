/**
 * 
 */
package com.aces.aws.controller;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.aces.aws.domain.EmailDto;
import com.aces.aws.domain.RegisterDto;
import com.aces.aws.entity.User;
import com.aces.aws.entity.UserToken;
import com.aces.aws.service.EmailService;
import com.aces.aws.service.ReCaptchaService;
import com.aces.aws.service.UserService;
import com.aces.aws.service.UserTokenService;

/**
 * @author aagarwal
 *
 */
@RestController
public class RegisterController  extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(RegisterController.class);
	
	private static final String CAPTCHA_MESSAGE_KEY = "captchaMessage";

	@Autowired
	private ReCaptchaService reCaptchaService;
	
	@Autowired
	private UserService userService;
	
    @Autowired
    private UserTokenService userTokenService;
    
	@Autowired
	private MessageSource messageSource;
	
    @Value("${aces.server.name}")
    private String serverName;    
    
    @Value("${aces.server.port}")
    private String serverPort;   
    
    @Value("${password.reset.email.from}")
    private String emailFrom;
    
	@Autowired
	private EmailService emailService;
	/**
	 * 
	 * @return
	 */	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public Map<String,Object> register(
		@RequestBody 
		@Valid
		RegisterDto dto
	,	HttpServletRequest request
	){
		LOGGER.debug("Inside register() for RegisterController");
		Map<String,Object> result = new HashMap<>();	
		if(!reCaptchaService.isCaptchaValid(dto.getRecaptchaResponse())){
			result.put("error", true);
			result.put(CAPTCHA_MESSAGE_KEY, messageSource.getMessage(CAPTCHA_MESSAGE_KEY,null,null));
		} else {
			User user = new User(dto.getEmail(), dto.getPassword(), dto.getName());
			userService.createUser(user);
			UserToken token = userTokenService.createAccountActivationToken(user);
            if (null != token) {
    			EmailDto emailDto = new EmailDto();
    	        emailDto.setTo(user.userName);
    	        emailDto.setSubject("[AwsAces]: Activate your Account");
    	        emailDto.setMessage(
    	        	  messageSource.getMessage("accountactivation.email.text", null, null)
    	        	+ "\r\n" 
    	        	+ createAccountActivationUrl(request, token)
    	        );
    	        emailDto.setFrom(emailFrom);
    	        emailService.sendEmail(emailDto);
    	    }
			result.put("successFullRegistrationMessage", messageSource.getMessage("successFullRegistrationMessage",null,null));		
			sendMailToSelf(dto);
		}
		return result;
	}
    /**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/activateAccount")
	public Map<String,Object> activateAccount(String token){
		LOGGER.debug("Inside activateAccount() for ResetPasswordController");
		UserToken userToken = userTokenService.findByToken(token);
		Map<String,Object> result = new HashMap<>();				
		boolean invalidLink = false;
		if(userToken!=null){
			if (LocalDateTime.now(Clock.systemUTC()).isAfter(userToken.expiryDate)) {
				invalidLink = true;
			} else {
				userService.activateAccount(userToken.userName);
			}
		} else {
			invalidLink = true;
		}
		if(invalidLink){
			result.put("invalidLink", true);			
		} else {			
			result.put("message","Your account is now Active. Please login using your Email/Password");
		}
		return result;
	}
	/**
	 * 
	 * @param request
	 * @param userId
	 * @param token
	 * @return
	 */
	private String createAccountActivationUrl(HttpServletRequest request, UserToken passwordResetToken) {
        StringBuilder sb = new StringBuilder();
        sb.append(request.getScheme());
        sb.append("://");
        sb.append(serverName);
        if(StringUtils.isNotBlank(serverPort)){
        	sb.append(":");
        	sb.append(serverPort);
        }
        sb.append(request.getContextPath());
        sb.append("#activateAccountPath");
        sb.append("?token=");
        sb.append(passwordResetToken.token);
        LOGGER.debug("Account Activation URL {}", sb.toString());
        return sb.toString();
    }
	/**
	 * 
	 */
	private void sendMailToSelf(RegisterDto dto){
		EmailDto emailDto = new EmailDto();
        emailDto.setTo(emailFrom);
        emailDto.setSubject("[AwsAces]: New Account Created");
        emailDto.setMessage(
        	  dto.getName()
        	+ "\r\n" 
        	+ dto.getEmail()
        );
        emailDto.setFrom(emailFrom);
        emailService.sendEmail(emailDto);
	}
}
