/**
 * 
 */
package com.aces.aws.domain;

import javax.validation.constraints.AssertTrue;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;

import com.aces.aws.infra.BaseDto;
import com.aces.aws.security.PasswordPolicy;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author aagarwal
 *
 */
public class ChangePasswordDto extends BaseDto {	

	private static final long serialVersionUID = 2757582170333420220L;
	
	@NotEmpty
	private String currentPassword;

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
	 * 
	 * @return
	 */
	@AssertTrue
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
	@AssertTrue
	@JsonIgnore
	public boolean isPasswordValid(){
		if(StringUtils.isNotBlank(password) && StringUtils.isNotBlank(confirmPassword)){
			return PasswordPolicy.isValid(confirmPassword);
		}
		return true;
	}
	/**
	 * @return the currentPassword
	 */
	public String getCurrentPassword() {
		return currentPassword;
	}
	/**
	 * @param currentPassword the currentPassword to set
	 */
	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}
}
