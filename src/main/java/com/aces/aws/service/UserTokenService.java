package com.aces.aws.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aces.aws.entity.UserToken;
import com.aces.aws.entity.User;
import com.aces.aws.repositories.UserTokenRepository;
import com.aces.aws.repositories.UserRepository;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;
/**
 * 
 * @author aagarwal
 *
 */
@Service
@Transactional
public class UserTokenService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTokenRepository userTokenRepository;

    @Value("${passwordtoken.expiration.length.minutes}")
    private int passwordTokenExpirationInMinutes;
    
    @Value("${accountactivationtoken.expiration.length.minutes}")
    private int accountActivationTokenExpirationInMinutes;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserTokenService.class);
    /**
     * 
     * @param email
     * @return
     */
    public UserToken createResetPasswordToken(String userName) {
        UserToken passwordResetToken = null;
        User user = userRepository.findByUserName(userName);
        if (null != user) {
            String token = UUID.randomUUID().toString();
            LocalDateTime now = LocalDateTime.now(Clock.systemUTC());
            passwordResetToken = new UserToken(userName, token, now, passwordTokenExpirationInMinutes);
            passwordResetToken = userTokenRepository.save(passwordResetToken);
            LOGGER.debug("Successfully created token {}  for user {}", token, user.getUsername());
        } else {
        	LOGGER.warn("We couldn't find a user for the given email {}", userName);
        }
        return passwordResetToken;
    }
    /**
     * 
     * @param email
     * @return
     */
    public UserToken createAccountActivationToken(User user) {
        UserToken passwordResetToken = new UserToken(
        									user.userName
        								,	UUID.randomUUID().toString()        	
        								, 	LocalDateTime.now(Clock.systemUTC())
        								, 	accountActivationTokenExpirationInMinutes
        								);
        return userTokenRepository.save(passwordResetToken);
    }

    /**
     * Retrieves a Password Reset Token for the given token id.
     * @param token The token to be returned
     * @return A Password Reset Token if one was found or null if none was found.
     */
    public UserToken findByToken(String token) {
        return userTokenRepository.findByToken(token);
    }
}
