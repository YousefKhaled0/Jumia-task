package com.jumia.exceptions.handler;

import com.jumia.exceptions.BadSearchCriteriaException;
import com.jumia.exceptions.CountryISOCodeNotFoundException;
import com.jumia.exceptions.CountryNameNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(CountryNameNotFoundException.class)
	public ModelAndView handleCountryNameNotFoundException(HttpServletRequest req,
			CountryNameNotFoundException ex) {
		return handleBadRequestErrors(req, ex);
	}

	@ExceptionHandler(BadSearchCriteriaException.class)
	public ModelAndView handleBadSearchCriteriaException(HttpServletRequest req,
			BadSearchCriteriaException ex) {
		return handleBadRequestErrors(req, ex);
	}

	@ExceptionHandler(CountryISOCodeNotFoundException.class)
	public ModelAndView handleCountryISOCodeNotFoundException(HttpServletRequest req,
			CountryISOCodeNotFoundException ex) {
		return handleBadRequestErrors(req, ex);
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
