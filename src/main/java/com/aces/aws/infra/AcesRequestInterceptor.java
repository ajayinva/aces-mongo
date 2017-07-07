package com.aces.aws.infra;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.aces.aws.entity.ProductPlan;
import com.aces.aws.service.UserService;
/**
 *
 * @author aagarwal
 *
 */
@Component
public class AcesRequestInterceptor extends HandlerInterceptorAdapter {
	/**
	 *
	 */
	public static final String NOT_AUTHORIZED = "1234567890";
	
	@Autowired
	private UserService userService;
	/**
	 *
	 */
	@Override
	public boolean preHandle(
		HttpServletRequest request
	,	HttpServletResponse response
	, 	Object handler
	) throws Exception {				
		System.out.println(request.getRequestURL());
		String planId = request.getParameter("planId");		
		List<ProductPlan> productPlans = userService.getUserProductPlans();
		boolean isAuthorized = false;
		if(StringUtils.isNotBlank(planId)){
			for(ProductPlan plan : productPlans) {
				if(plan.id.equals(new Long(planId))){
					isAuthorized = true;
				}
			}	
		}
		if(!isAuthorized){
			throw new AcesException(NOT_AUTHORIZED,"Not Authorized");
		}
		return isAuthorized;
	}
	/**
	 *
	 */
	@Override
	public void postHandle(
		HttpServletRequest request
	,	HttpServletResponse response
	, 	Object handler
	, 	ModelAndView modelAndView
	) throws Exception {
		
	}
}
