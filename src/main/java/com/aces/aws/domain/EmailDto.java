/**
 * 
 */
package com.aces.aws.domain;

import java.io.Serializable;

/**
 * @author aagarwal
 *
 */
public class EmailDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 601641879232955257L;
	
	private String to;
	
	private String from;
	
	private String subject;
	
	private String message;

	/**
	 * @return the from
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * @param from the from to set
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
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
	/**
	 * 
	 */
	@Override
	public String toString() {
		return "EmailDto [from=" + from + ", subject=" + subject + ", message=" + message + "]";
	}

	/**
	 * @return the to
	 */
	public String getTo() {
		return to;
	}

	/**
	 * @param to the to to set
	 */
	public void setTo(String to) {
		this.to = to;
	}
}
