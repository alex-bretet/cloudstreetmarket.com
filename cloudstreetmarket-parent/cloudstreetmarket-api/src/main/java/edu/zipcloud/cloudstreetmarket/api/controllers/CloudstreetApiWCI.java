package edu.zipcloud.cloudstreetmarket.api.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;

import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

import edu.zipcloud.cloudstreetmarket.api.exceptions.ErrorInfo;

public class CloudstreetApiWCI extends WebContentInterceptor {

    private Logger log = Logger.getLogger(CloudstreetApiWCI.class);
    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	public CloudstreetApiWCI(){
		setRequireSession(false);
		setCacheSeconds(0);
		setSupportedMethods("GET","POST", "OPTIONS", "HEAD");
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException {
		super.preHandle(request, response, handler);
		//Define your custom pre-processing here
		return true;
	}
	
	@Override
	public void postHandle(
			HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		//Define your custom post-processing here
	}

	@Override
	public void afterCompletion(
			HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		//Define your custom after-completion here
	}
	
	@InitBinder
	public void allowEmptyDateBinding( WebDataBinder binder )
	{
		synchronized(df) {
			binder.registerCustomEditor( Date.class, new CustomDateEditor( df, true ));
			binder.registerCustomEditor( String.class, new StringTrimmerEditor( true ));
		}
	}
	
	@ExceptionHandler({ConversionNotSupportedException.class, IllegalArgumentException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorInfo badRequestHandler(HttpServletRequest req, HttpServletResponse resp, Exception ex) throws Exception {
		log.debug("Response: "+HttpStatus.BAD_REQUEST+": "+ex.getMessage());
		return new ErrorInfo(ex);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	@ResponseBody
	public ErrorInfo conflictHandler(HttpServletRequest req, HttpServletResponse resp, Exception ex) throws Exception {
		log.debug("Response: "+HttpStatus.CONFLICT+": "+ex.getMessage());
		return new ErrorInfo(ex);
	}
	
	@ExceptionHandler({NoSuchElementException.class, NoResultException.class})
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ErrorInfo noResultHandler(HttpServletRequest req, HttpServletResponse resp, Exception ex) throws Exception {
		log.debug("Response: "+HttpStatus.NOT_FOUND+": "+ex.getMessage());
		return new ErrorInfo(ex);
	}
	
	@ExceptionHandler({Exception.class})
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ErrorInfo errorHandler(HttpServletRequest req, HttpServletResponse resp, Exception ex) throws Exception {
		log.debug("Response: "+HttpStatus.INTERNAL_SERVER_ERROR+": "+ex.getMessage());
		return new ErrorInfo(ex);
	}
}
