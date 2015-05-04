package edu.zipcloud.cloudstreetmarket.api.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import edu.zipcloud.cloudstreetmarket.core.services.CommunityService;

public class CustomBasicAuthenticationEntryPoint extends
        BasicAuthenticationEntryPoint {

    @Autowired
    private CommunityService communityService;
	
	@Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setHeader("WWW-Authenticate", "CSM_Basic realm=\"" + getRealmName() + "\"");
    }

}
