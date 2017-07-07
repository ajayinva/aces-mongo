/**
 * 
 */
package com.aces.aws.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
/**
 * @author aagarwal
 *
 */
@Configuration
@Profile("dev")
//@PropertySource("classpath:application_dev.properties")
@PropertySource("file:///${user.home}//projects//aces//src//main//resources//application_dev.properties")
public class DevelopmentConfig {

}
