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
@Profile("prod")
@PropertySource("file:///${user.home}//application_prod.properties")
public class ProductionConfig {

}
