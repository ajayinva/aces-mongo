/**
 * 
 */
package com.aces.aws.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aces.aws.entity.ProductPlan;
import com.aces.aws.entity.User;
import com.aces.aws.infra.GlobalFunctions;
import com.aces.aws.service.UserService;

/**
 * @author aagarwal
 *
 */
@Component
public class BaseController {
	@Autowired
	private UserService userService;
	/**
	 * 
	 * @return
	 */
	protected User getCurrentUser(){
		return GlobalFunctions.getCurrentUser();
	}
	/**
	 * 
	 * @param productCode
	 * @return
	 */
	/*protected ProductPlan getUserProductPlan(Integer planId){
		List<ProductPlan> productPlans = userService.getUserProductPlans();
		for(ProductPlan plan : productPlans) {
			if(plan.getProduct().getCode().equals(productCode)){
				return plan;
			}
		}
		return null;
	}*/
	
	
}
