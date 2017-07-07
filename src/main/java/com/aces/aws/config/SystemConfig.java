/**
 * 
 */
package com.aces.aws.config;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpStatus;

import com.aces.aws.service.EmailService;
/**
 * @author aagarwal
 *
 */
@Configuration
public class SystemConfig {
	/**
	 * 
	 * @return
	 */
	@Bean
	public ReloadableResourceBundleMessageSource messageSource(){
		ReloadableResourceBundleMessageSource r = new ReloadableResourceBundleMessageSource();
		r.setBasename("classpath:messages");
		r.setCacheSeconds(1800);
		return r;
	}
	/**
	 * 
	 * @return
	 */
	@Bean
	public EmailService emailService(){
		return new EmailService();
	}
	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {
	 
	    return new EmbeddedServletContainerCustomizer() {
	        @Override
	        public void customize(ConfigurableEmbeddedServletContainer container) {	 
	            ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/index.html");
	            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/index.html");
	            ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/index.html");	
	            container.addErrorPages(error401Page, error404Page, error500Page);
	        }
	    };
	}

}
