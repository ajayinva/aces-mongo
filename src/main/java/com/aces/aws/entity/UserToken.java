package com.aces.aws.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;
/**
 * 
 * @author aagarwal
 *
 */
@Document(collection="user_tokens")
public class UserToken implements Serializable {
	private static final long serialVersionUID = -7583987417262239745L;
	
	public String token;

	public LocalDateTime expiryDate;
	
	public String userName;
    /**
     * 
     * @param token
     * @param user
     * @param creationDateTime
     * @param expirationInMinutes
     */
    public UserToken(String userName, String token, LocalDateTime creationDateTime, int expirationInMinutes) {
        this.token = token;
        this.userName = userName;
        expiryDate = creationDateTime.plusMinutes(expirationInMinutes);
    }
}
