package com.aces.aws.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.aces.aws.entity.User;
/**
 * 
 * @author aagarwal
 *
 */
@Repository
public interface UserRepository extends CrudRepository<User, String> {
	
	User findByUserName(String username);
}
