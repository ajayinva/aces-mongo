/**
 * 
 */
package com.aces.aws.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author aagarwal
 *
 */
public class Authority implements GrantedAuthority {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4861725661823037128L;
	
	private final String authority;
	
	public Authority(String value){
		this.authority = value;
	}
	
	@Override
	public String getAuthority(){
		return authority;
	}
}
