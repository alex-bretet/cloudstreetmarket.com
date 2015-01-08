package edu.zipcloud.cloudstreetmarket.api.controllers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

public class CloudstreetApiWCI extends WebContentInterceptor {

	public CloudstreetApiWCI(){
		setRequireSession(false);
		setCacheSeconds(0);
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
}
