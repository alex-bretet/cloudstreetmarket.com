package edu.zipcloud.cloudstreetmarket.api.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

public class CustomBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

	private final String SWAGGER_UI_PATH = "/api/index.html";
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
		String referer = (String) request.getHeader("referer");
		if(referer != null && referer.contains(SWAGGER_UI_PATH)){
			super.commence(request, response, authException);
			return;
		}
		response.setHeader("WWW-Authenticate", "CSM_Basic realm=\"" + getRealmName() + "\"");
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
		
	}
}
