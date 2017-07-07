package com.aces.aws.entity;


import java.io.Serializable;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.aces.aws.security.Authority;
/**
 * @author aagarwal
 *
 */
@Document(collection="users")
public class User implements Serializable, UserDetails  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4655924364942953691L;
	@Id
	public String id;
		
	public String userName;
	
	public String password;
	
	public String name;
		
	public boolean enabled;
		
    public LocalDateTime registrationDate;
    
    public List<QuestionNote> notes = new ArrayList<>();
    
    public List<UserProduct> products = new ArrayList<>();
    
    public User (String userName, String password, String name){
    	this.userName = userName;
    	this.password = password;
    	this.name = name;
    	this.registrationDate = LocalDateTime.now(Clock.systemUTC());
    }
    /**
	 * 
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<>();		
		authorities.add(new Authority("ROLE_BASIC"));
		return authorities;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return userName;
	}
	/**
	 * 
	 */
	public boolean isAccountNonExpired() {
		return true;
	}
	/**
	 * 
	 */
	public boolean isAccountNonLocked() {
		return true;
	}
	/**
	 * 
	 */
	public boolean isCredentialsNonExpired() {
		return true;
	}
	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	/**
	 * 
	 */
	public boolean isEnabled() {
		return this.enabled;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}
}
