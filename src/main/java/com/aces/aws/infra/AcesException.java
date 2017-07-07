/**
 * 
 */
package com.aces.aws.infra;

/**
 * @author aagarwal
 *
 */
public class AcesException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9133562718023878029L;
	
	
	private String messageId;
	
	private String message;
	
	/**
	 * 
	 * @param messageId
	 */
	public AcesException(String messageId) {
        this.messageId = messageId;
    }
	/**
	 * 
	 * @param messageId
	 */
	public AcesException(String messageId, String message) {
        this.messageId = messageId;
        this.message = message;
    }
	/**
	 * @return the messageId
	 */
	public String getMessageId() {
		return messageId;
	}

	/**
	 * @param messageId the messageId to set
	 */
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
