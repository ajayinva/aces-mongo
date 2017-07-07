package com.aces.aws.infra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 *
 * @author aagarwal
 *
 */
public class AcesErrorResponse extends BaseDto {
	/**
	 *
	 */
	private static final long serialVersionUID = -3420547118256298032L;
	/**
	 *
	 */
	private boolean error = true;
	/**
	 *
	 */
	private List<AcesMessage> messageList = new ArrayList<>();
	/**
	 *
	 */
	private Map<String,String> messages = new HashMap<String,String>();
	/**
	 *
	 * @return
	 */
	public List<AcesMessage> getMessageList() {
		return messageList;
	}
	/**
	 *
	 * @param messageList
	 */
	public void setMessageList(List<AcesMessage> messageList) {
		this.messageList = messageList;
	}
	/**
	 * @return the messages
	 */
	public Map<String, String> getMessages() {
		return messages;
	}
	/**
	 * @param messages the messages to set
	 */
	public void setMessages(Map<String, String> messages) {
		this.messages = messages;
	}
	/**
	 * @return the error
	 */
	public boolean isError() {
		return error;
	}
	/**
	 * @param error the error to set
	 */
	public void setError(boolean error) {
		this.error = error;
	}
}
