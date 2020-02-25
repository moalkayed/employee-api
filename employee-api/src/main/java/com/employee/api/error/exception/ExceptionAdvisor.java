package com.employee.api.error.exception;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.employee.api.error.DefaultError;
import com.employee.api.error.ErrorWrapper;
import com.employee.api.error.Fault;

@ControllerAdvice
public class ExceptionAdvisor {

	private final String ERROR_MESSAGES_FILE_NAME = "ErrorMessages";

	private Logger log = LoggerFactory.getLogger(ExceptionAdvisor.class);

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<Object> handleHttpMessageNotReadableExceptionBadRequests(
			HttpMessageNotReadableException exception, HttpServletResponse httpServletResponse) {

		exception.printStackTrace();
		log.error(exception.getMessage());

		ErrorWrapper errorWrapper = new ErrorWrapper();

		Fault fault = new Fault(DefaultError.CLIENT_SIDE_ERROR_BAD_REQUEST);

		errorWrapper.addFault(fault, "ar", ERROR_MESSAGES_FILE_NAME);

		HttpStatus httpValue = HttpStatus.valueOf(errorWrapper.getResponseCode());
		return new ResponseEntity<Object>(errorWrapper, httpValue);
	}

	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public ResponseEntity<Object> handleHttpMediaTypeNotSupportedExceptionBadRequests(
			HttpMediaTypeNotSupportedException exception, HttpServletResponse httpServletResponse) {

		exception.printStackTrace();
		log.error(exception.getMessage());

		ErrorWrapper errorWrapper = new ErrorWrapper();

		Fault fault = new Fault(DefaultError.CLIENT_SIDE_ERROR_CONTENT_TYPE_JSON);

		errorWrapper.addFault(fault, "ar", ERROR_MESSAGES_FILE_NAME);

		HttpStatus httpValue = HttpStatus.valueOf(errorWrapper.getResponseCode());

		return new ResponseEntity<Object>(errorWrapper, httpValue);
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<Object> handleMissingServletRequestParameterException(Exception exception,
			HttpServletResponse httpServletResponse) {

		exception.printStackTrace();
		log.error(exception.getMessage());

		ErrorWrapper errorWrapper = new ErrorWrapper();

		Fault fault = new Fault(DefaultError.CLIENT_SIDE_ERROR_BAD_REQUEST);

		errorWrapper.addFault(fault, "ar", ERROR_MESSAGES_FILE_NAME);

		HttpStatus httpValue = HttpStatus.valueOf(errorWrapper.getResponseCode());
		return new ResponseEntity<Object>(errorWrapper, httpValue);
	}

	@ExceptionHandler(EmployeeException.class)
	public ResponseEntity<Object> handleEmployeeException(EmployeeException exception,
			HttpServletResponse httpServletResponse) {

		exception.printStackTrace();
		log.error(exception.getMessage());

		ErrorWrapper errorWrapper = new ErrorWrapper();

		Fault fault = new Fault(exception.getError());

		errorWrapper.addFault(fault, "ar", ERROR_MESSAGES_FILE_NAME);

		HttpStatus httpValue = HttpStatus.valueOf(errorWrapper.getResponseCode());
		return new ResponseEntity<Object>(errorWrapper, httpValue);
	}

	@ExceptionHandler(GenericException.class)
	public ResponseEntity<Object> handleEntityNotFoundException(GenericException exception,
			HttpServletResponse httpServletResponse) {

		exception.printStackTrace();
		log.error(exception.getMessage());

		ErrorWrapper errorWrapper = new ErrorWrapper();

		Fault fault = new Fault(DefaultError.INTERNAL_SERVER_ERROR);

		errorWrapper.addFault(fault, "ar", ERROR_MESSAGES_FILE_NAME);

		HttpStatus httpValue = HttpStatus.valueOf(errorWrapper.getResponseCode());
		return new ResponseEntity<Object>(errorWrapper, httpValue);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleServiceException(Exception exception, HttpServletResponse httpServletResponse) {

		exception.printStackTrace();
		log.error(exception.getMessage());

		ErrorWrapper errorWrapper = new ErrorWrapper();

		Fault fault = new Fault(DefaultError.INTERNAL_SERVER_ERROR);

		errorWrapper.addFault(fault, "ar", ERROR_MESSAGES_FILE_NAME);

		HttpStatus httpValue = HttpStatus.valueOf(errorWrapper.getResponseCode());
		return new ResponseEntity<Object>(errorWrapper, httpValue);
	}

}
