package com.aces.aws.infra;
/**
 *
 * @author aagarwal
 *
 */
public class AcesMessage extends BaseDto{
	/**
	 *
	 */
	private static final long serialVersionUID = -7184993339001864310L;
	/**
	 *
	 */
	private String key = null;
	/**
	 *
	 */
	private String message = null;
	/*
	 *
	 */
	public AcesMessage(){
		//Default Contructor
	}
	/**
	 *
	 * @param message
	 */
	public AcesMessage(String message){
		this.message = message;
	}
	/**
	 *
	 * @param message
	 */
	public AcesMessage(String key, String message){
		this.key = key;
		this.message = message;
	}
	/**
	 *
	 * @param message
	 */
	public AcesMessage(Integer key, String message){
		this.key = key+"";
		this.message = message;
	}
	/**
	 *
	 * @return
	 */
	public String getKey() {
		return key;
	}
	/**
	 *
	 * @param key
	 */
	public void setKey(String key) {
		this.key = key;
	}
	/**
	 *
	 * @return
	 */
	public String getMessage() {
		return message;
	}
	/**
	 *
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 *
	 */
	public String toString(){
		return "key:"+key+" message:"+message;
	}
}
