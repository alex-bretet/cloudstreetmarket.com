package edu.zipcloud.cloudstreetmarket.api.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

@Component
public class CloudstreetApiWCI extends WebContentInterceptor {

    private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

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
	public void allowDateBinding ( WebDataBinder binder )
	{
		binder.registerCustomEditor( Date.class, new CustomDateEditor( df, true ));
	}                                                          
	
}
