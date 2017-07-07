/**
 * 
 */
package com.aces.aws.domain;

import com.aces.aws.infra.BaseDto;

/**
 * @author aagarwal
 *
 */
public class MyProductButton extends BaseDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8925527463413540954L;
	private Long planId;
	private boolean tryNow = true;
	private boolean buyNow = true;
	private boolean enter = false;
	/**
	 * @return the tryNow
	 */
	public boolean isTryNow() {
		return tryNow;
	}
	/**
	 * @param tryNow the tryNow to set
	 */
	public void setTryNow(boolean tryNow) {
		this.tryNow = tryNow;
	}
	/**
	 * @return the buyNow
	 */
	public boolean isBuyNow() {
		return buyNow;
	}
	/**
	 * @param buyNow the buyNow to set
	 */
	public void setBuyNow(boolean buyNow) {
		this.buyNow = buyNow;
	}
	/**
	 * @return the enter
	 */
	public boolean isEnter() {
		return enter;
	}
	/**
	 * @param enter the enter to set
	 */
	public void setEnter(boolean enter) {
		this.enter = enter;
	}
	/**
	 * @return the planId
	 */
	public Long getPlanId() {
		return planId;
	}
	/**
	 * @param planId the planId to set
	 */
	public void setPlanId(Long planId) {
		this.planId = planId;
	}
}
