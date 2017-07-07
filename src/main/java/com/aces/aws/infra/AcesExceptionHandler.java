package com.aces.aws.infra;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
/**
 *
 * @author aagarwal
 *
 */
@ControllerAdvice
public class AcesExceptionHandler {
	/**
	 * 
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(AcesExceptionHandler.class);
	/**
	 *
	 */
	public static final String ERROR_ALERT = "error.alert";

	@Autowired
	private MessageSource messageSource;
	/**
	 *
	 * @param manve
	 * @param request
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleValidationError(
		MethodArgumentNotValidException manve
	, 	HttpServletRequest request
	) {
		AcesErrorResponse response = new AcesErrorResponse();
		List<FieldError> fieldErrors = manve.getBindingResult().getFieldErrors();
		for(FieldError fe : fieldErrors) {
			String message = response.getMessages().get(fe.getField());
			String newMessage = messageSource.getMessage(fe, null);
			String fieldName = fe.getField();
			if(StringUtils.isBlank(message)){
				response.getMessages().put(fieldName,newMessage);
			}
			else{
				response.getMessages().put(fieldName, message+","+newMessage);
			}

		}
		AcesMessage message = new AcesMessage(ERROR_ALERT,messageSource.getMessage(ERROR_ALERT, null,null));
		response.getMessageList().add(message);
		return new ResponseEntity<>(response, null, HttpStatus.OK);
	}
	/**
	 *
	 * @param pex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(AcesException.class)
	public ResponseEntity<?> handleException(
		AcesException acesException
	, 	HttpServletRequest request
	) {
		AcesErrorResponse response = new AcesErrorResponse();
		if(AcesRequestInterceptor.NOT_AUTHORIZED.equals(acesException.getMessageId())) {
			return new ResponseEntity<>(response, null, HttpStatus.FORBIDDEN);
		}				
		AcesMessage message = new AcesMessage(acesException.getMessageId(),messageSource.getMessage(acesException.getMessageId(), null,null));
		response.getMessageList().add(message);
		return new ResponseEntity<>(response, null, HttpStatus.OK);
	}
	/**
	 *
	 * @param pex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleException(
		Exception ex
	, 	HttpServletRequest request
	) {
		LOGGER.debug(getStackTraceString(ex));
		AcesErrorResponse response = new AcesErrorResponse();
		AcesMessage message = new AcesMessage(9999,"The system has encountered an error. Please contact your system administrator");
		response.getMessageList().add(message);
		return new ResponseEntity<>(response, null, HttpStatus.OK);
	}
	/**
	 * 
	 * @return
	 */
    public String getStackTraceString(Exception ex)
    {
        final StringWriter stringWriter = new StringWriter();
        ex.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }
}
