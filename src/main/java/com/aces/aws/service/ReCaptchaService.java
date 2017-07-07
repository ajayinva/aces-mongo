/**
 * 
 */
package com.aces.aws.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author aagarwal
 *
 */
@Service
public class ReCaptchaService {
	/**
	 * 
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ReCaptchaService.class);

	/**
	 * 
	 */
	private RestTemplate restTemplate  = new RestTemplate();
	/**
	 * 
	 */
	@Value("${captcha.secret}")
	private String captchaSecret;
	/**
	 * 
	 */
	@Value("${captcha.url}")
	private String captchaUrl;
	/**
	 * 
	 * @param captchaResponse
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean isCaptchaValid(String captchaResponse){
		LOGGER.debug("Inside isCaptchaValid() for ReCaptchaService");
		String recaptchaRequest = new StringBuilder()
                .append(captchaUrl)
                .append("?secret=")
                .append(captchaSecret)
                .append("&response=")
                .append(captchaResponse)
                .toString();	
		LOGGER.debug("recaptchaRequest {}", recaptchaRequest);
		Map<Object, Object> responseFromGoogle = restTemplate.postForObject(recaptchaRequest,null,HashMap.class);
		LOGGER.debug("responseFromGoogle {}", responseFromGoogle);
		return (Boolean) responseFromGoogle.get("success");
	}
}
