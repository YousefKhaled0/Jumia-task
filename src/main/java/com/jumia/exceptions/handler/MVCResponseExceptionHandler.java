package com.jumia.exceptions.handler;

import com.jumia.controllers.MVCSearchController;
import com.jumia.exceptions.BadSearchCriteriaException;
import com.jumia.exceptions.CountryISOCodeNotFoundException;
import com.jumia.exceptions.CountryNameNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice(assignableTypes = MVCSearchController.class)
public class MVCResponseExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(CountryNameNotFoundException.class)
	public ModelAndView handleCountryNameNotFoundException(HttpServletRequest req, CountryNameNotFoundException ex) {
		return handleBadRequestErrors(req, ex);
	}

	@ExceptionHandler(BadSearchCriteriaException.class)
	public ModelAndView handleBadSearchCriteriaException(HttpServletRequest req, BadSearchCriteriaException ex) {
		return handleBadRequestErrors(req, ex);
	}

	@ExceptionHandler(CountryISOCodeNotFoundException.class)
	public ModelAndView handleCountryISOCodeNotFoundException(HttpServletRequest req,
			CountryISOCodeNotFoundException ex) {
		return handleBadRequestErrors(req, ex);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ModelAndView handleMethodArgumentTypeMismatchException(HttpServletRequest req,
			MethodArgumentTypeMismatchException ex) {
		logger.error("Request: " + req.getRequestURL() + " raised " + ex);

		ModelAndView errorResponse = new ModelAndView();
		errorResponse.addObject("isBad", true);
		errorResponse.addObject("error", String.format("Wrong value for %s", ex.getParameter().getParameterName()));
		errorResponse.setViewName("index");
		errorResponse.setStatus(HttpStatus.BAD_REQUEST);
		return errorResponse;
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
