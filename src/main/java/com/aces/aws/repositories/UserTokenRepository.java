package com.aces.aws.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.aces.aws.entity.UserToken;

/**
 * 
 * @author aagarwal
 *
 */
@Repository
public interface UserTokenRepository extends CrudRepository<UserToken, Long> {

    UserToken findByToken(String token);

    List<UserToken> findAllByUserName(String userName);
}
