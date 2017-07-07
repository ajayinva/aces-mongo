/**
 * 
 */
package com.aces.aws.infra;

import java.io.Serializable;

/**
 * @author aagarwal
 *
 */
public class BaseDto  implements Serializable {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1096189487196742474L;
	/**
	 * 
	 */
	private String dtoName = this.getClass().getSimpleName();
	/**
	 * @return the dtoName
	 */
	public String getDtoName() {
		return dtoName;
	}
	/**
	 * @param dtoName the dtoName to set
	 */
	public void setDtoName(String dtoName) {
		this.dtoName = dtoName;
	}

}
