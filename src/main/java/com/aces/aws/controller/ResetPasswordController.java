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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aces.aws.domain.EmailDto;
import com.aces.aws.domain.PasswordResetDto;
import com.aces.aws.entity.UserToken;
import com.aces.aws.service.EmailService;
import com.aces.aws.service.UserTokenService;
import com.aces.aws.service.UserService;
/**
 * @author aagarwal
 *
 */
@RestController
public class ResetPasswordController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ResetPasswordController.class);
    @Autowired
    private UserTokenService userTokenService;
    
	@Autowired
	private MessageSource messageSource;
    
	@Autowired
	private EmailService emailService;
	
    @Autowired
    private UserService userService;
    
    @Value("${aces.server.name}")
    private String serverName;    
    
    @Value("${aces.server.port}")
    private String serverPort;   
    
    @Value("${password.reset.email.from}")
    private String emailFrom; 
    /**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	public Map<String,Object> resetPassword(
		@RequestBody 
		@Valid
		PasswordResetDto dto
	){
		LOGGER.debug("Inside resetPassword() for ResetPasswordController");
		UserToken passwordResetToken = userTokenService.findByToken(dto.getToken());
		Map<String,Object> result = new HashMap<>();				
		boolean invalidLink = false;
		if(passwordResetToken!=null){
			String userName = passwordResetToken.userName;
			if(StringUtils.isNotBlank(userName)){			
				if(!userName.equals(dto.getEmail())){
					invalidLink = true;
				} else{
					userService.updatePassword(userName, dto.getPassword());
				}
			}
			if (LocalDateTime.now(Clock.systemUTC()).isAfter(passwordResetToken.expiryDate)) {
				invalidLink = true;
			}
		} else {
			invalidLink = true;
		}
		if(invalidLink){
			result.put("invalidLink", true);			
		} else {			
			result.put("message","Your password is reset. Please login using your new Password");
		}
		return result;
	}
	/**
	 * 
	 * @return
	 */	
	@RequestMapping(value = "/sendPasswordResetEmail", method = RequestMethod.POST)
	public Map<String,Object> sendEmail(HttpServletRequest request, @RequestParam("email") String email){
		LOGGER.debug("Inside register() for PasswordResetController");
		UserToken passwordResetToken = userTokenService.createResetPasswordToken(email);
		if (null != passwordResetToken) {
			EmailDto emailDto = new EmailDto();
	        emailDto.setTo(email);
	        emailDto.setSubject("[AwsAces]: How to Reset Your Password");
	        emailDto.setMessage(
	        	  messageSource.getMessage("forgotmypassword.email.text", null, null)
	        	+ "\r\n" 
	        	+ createPasswordResetUrl(request, passwordResetToken)
	        );
	        emailDto.setFrom(emailFrom);
	        emailService.sendEmail(emailDto);
	    }
		Map<String,Object> result = new HashMap<>();
		result.put("mailSent",true);
		return result;
	}
	/**
	 * 
	 * @param request
	 * @param userId
	 * @param token
	 * @return
	 */
	private String createPasswordResetUrl(HttpServletRequest request, UserToken passwordResetToken) {
        StringBuilder sb = new StringBuilder();
        sb.append(request.getScheme());
        sb.append("://");
        sb.append(serverName);
        if(StringUtils.isNotBlank(serverPort)){
        	sb.append(":");
        	sb.append(serverPort);
        }
        sb.append(request.getContextPath());
        sb.append("#resetPasswordPath");
        sb.append("?token=");
        sb.append(passwordResetToken.token);
        LOGGER.debug("Reset Password URL {}", sb.toString());
        return sb.toString();
    }
}
