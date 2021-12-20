package com.jumia.exceptions.handler;

import com.jumia.controllers.RestSearchController;
import com.jumia.custom.models.Error;
import com.jumia.exceptions.BadSearchCriteriaException;
import com.jumia.exceptions.CountryISOCodeNotFoundException;
import com.jumia.exceptions.CountryNameNotFoundException;
import com.jumia.exceptions.MinPageValueException;
import com.jumia.util.ErrorFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice(assignableTypes = RestSearchController.class)
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(CountryNameNotFoundException.class)
	public ResponseEntity<Error> handleCountryNameNotFoundException(HttpServletRequest req,
			CountryNameNotFoundException ex) {
		return ResponseEntity.badRequest().body(ErrorFactory.error(ex.getMessage(), 400));
	}

	@ExceptionHandler(BadSearchCriteriaException.class)
	public ResponseEntity<Error> handleBadSearchCriteriaException(HttpServletRequest req,
			BadSearchCriteriaException ex) {
		return ResponseEntity.badRequest().body(ErrorFactory.error(ex.getMessage(), 400));
	}

	@ExceptionHandler(CountryISOCodeNotFoundException.class)
	public ResponseEntity<Error> handleCountryISOCodeNotFoundException(HttpServletRequest req,
			CountryISOCodeNotFoundException ex) {
		return ResponseEntity.badRequest().body(ErrorFactory.error(ex.getMessage(), 400));
	}

	@ExceptionHandler(MinPageValueException.class)
	public ResponseEntity<Error> handleMinPageValueException(HttpServletRequest req,
			MinPageValueException ex) {
		return ResponseEntity.badRequest().body(ErrorFactory.error(ex.getMessage(), 400));
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<Error> handleMethodArgumentTypeMismatchException(HttpServletRequest req,
			MethodArgumentTypeMismatchException ex) {
		logger.error("Request: " + req.getRequestURL() + " raised " + ex);

		return ResponseEntity.badRequest()
				.body(ErrorFactory.error(String.format("Wrong value for %s", ex.getParameter().getParameterName()),
						400));
	}

	private ModelAndView handleBadRequestErrors(HttpServletRequest req, RuntimeException ex) {
		logger.error("Request: " + req.getRequestURL() + " raised " + ex);

		ModelAndView errorResponse = new ModelAndView();
		errorResponse.addObject("isBad", true);
		errorResponse.addObject("error", ex.getMessage());
		errorResponse.setViewName("index");
		errorResponse.setStatus(HttpStatus.BAD_REQUEST);
		return errorResponse;
	}
}
