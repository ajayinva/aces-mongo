package com.aces.aws.entity;

import java.io.Serializable;
/**
 * @author aagarwal
 *
 */
public class UserProduct implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 95835206900204261L;	
	
	public UserProduct(Long productId,Long planId){
		this.productId = productId;
		this.planId = planId;		
	}
	public Long productId;
	public Long planId;
}
