/**
 * 
 */
package com.aces.aws.domain;

import javax.validation.constraints.AssertTrue;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.aces.aws.infra.BaseDto;
import com.aces.aws.security.PasswordPolicy;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author aagarwal
 *
 */
public class PasswordResetDto extends BaseDto {	

	private static final long serialVersionUID = 2757582170333420220L;

	@NotEmpty
	@Email
	private String email;
	
	@NotEmpty
	private String token;
	
	@NotEmpty
	private String password;
	
	@NotEmpty
	private String confirmPassword;
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the confirmPassword
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}
	/**
	 * @param confirmPassword the confirmPassword to set
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}		
	/**
	 * 
	 * @return
	 */
	@AssertTrue(message="{Password.NotMatching}")
	@JsonIgnore
	public boolean isPasswordMatching(){
		if(StringUtils.isNotBlank(password) && StringUtils.isNotBlank(confirmPassword)){
			return password.equals(confirmPassword);
		}
		return true;
	}
	/**
	 * 
	 * @return
	 */
	@AssertTrue(message="{Password.NonCompliant}")
	@JsonIgnore
	public boolean isPasswordValid(){
		if(StringUtils.isNotBlank(password) && StringUtils.isNotBlank(confirmPassword)){
			return PasswordPolicy.isValid(confirmPassword);
		}
		return true;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
}
