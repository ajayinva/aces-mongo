/**
 * 
 */
package com.aces.aws.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.aces.aws.infra.AcesRequestInterceptor;

/**
 * @author aagarwal
 *
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	  @Autowired 
	  private AcesRequestInterceptor acesRequestInterceptor;

	  @Override
	  public void addInterceptors(InterceptorRegistry registry) {
	    registry.addInterceptor(acesRequestInterceptor).addPathPatterns("/p/**"); 
	  }
}