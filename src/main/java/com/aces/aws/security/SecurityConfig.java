package com.aces.aws.security;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
/**
 * 
 * @author aagarwal
 *
 */
@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	 private static final String SALT = "as&*(*o;i@#hyj3249sd@#$fndsipw3";
	
	 @Autowired
	 private UserSecurityService userSecurityService;
	
	 @Autowired
	 private MySavedRequestAwareAuthenticationSuccessHandler authenticationSuccessHandler;
	 
	 @Autowired
	 private SimpleUrlAuthenticationFailureHandler authenticationFailureHandler;
	 
	 @Autowired
	 private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
	 
	 @Autowired
	 private NoRedirectLogoutSuccessHandler logoutSuccessHandler;
	
	 private static final String [] EXEMPTED_RESOURCES = {
	    "/"
	 ,  "/assets/**"
	 ,	"/business/**"
	 ,	"/index.html"
	 ,	"/healthcheck.html"
	 ,	"/html/**"
	 ,	"/contact"
	 ,	"/user"
	 ,	"/register"
	 ,	"/sendPasswordResetEmail"
	 ,	"/resetPassword"
	 ,	"/activateAccount"
	 ,	"/product/viewAll"
	 ,	"/product/view"	 
	 };

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.exceptionHandling()
        	.authenticationEntryPoint(restAuthenticationEntryPoint)
        	.and()
			.authorizeRequests()
			.antMatchers(EXEMPTED_RESOURCES).permitAll()
			.anyRequest().authenticated()
			.and()
			.formLogin()
			.successHandler(authenticationSuccessHandler)
			.failureHandler(authenticationFailureHandler)
			.loginProcessingUrl("/login")
			.and()			
	        .logout()
	        .logoutUrl("/logout")
	        .logoutSuccessHandler(logoutSuccessHandler)
	        .and()
			.csrf()
			.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
	}
	/**
	 * 
	 * @param amb
	 * @throws Exception
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder amb) throws Exception{
		amb.userDetailsService(userSecurityService).passwordEncoder(passwordEncoder());
	}
	/**
	 * 
	 * @return
	 */
	@Bean
    public MySavedRequestAwareAuthenticationSuccessHandler mySuccessHandler(){
        return new MySavedRequestAwareAuthenticationSuccessHandler();
    }
	/**
	 * 
	 * @return
	 */
	@Bean
    public SimpleUrlAuthenticationFailureHandler myFailureHandler(){
        return new SimpleUrlAuthenticationFailureHandler();
    }
	/**
	 * 
	 * @return
	 */
	@Bean
    public NoRedirectLogoutSuccessHandler logoutSuccessHandler(){
        return new NoRedirectLogoutSuccessHandler();
    }
	/**
	 * 
	 * @return
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder(12, new SecureRandom(SALT.getBytes()));
	}
}