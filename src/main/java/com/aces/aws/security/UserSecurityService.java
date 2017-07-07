/**
 * 
 */
package com.aces.aws.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aces.aws.entity.User;
import com.aces.aws.repositories.UserRepository;

/**
 * @author aagarwal
 *
 */
@Service
public class UserSecurityService implements UserDetailsService{
	/**
	 * 
	 */
	@Autowired
	private UserRepository userRepository;
	/**
	 * 
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {	
		User user = userRepository.findByUserName(username);
		if(user==null){
			throw new UsernameNotFoundException("Username "+username+" not found");
		}
		return user;
	}
}
