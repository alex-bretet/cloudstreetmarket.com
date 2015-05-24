package edu.zipcloud.cloudstreetmarket.api.controllers;

import static edu.zipcloud.cloudstreetmarket.core.enums.Role.*;
import static javax.ws.rs.HttpMethod.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

import edu.zipcloud.cloudstreetmarket.core.entities.SocialUser;
import edu.zipcloud.cloudstreetmarket.core.entities.User;
import edu.zipcloud.cloudstreetmarket.core.services.CommunityService;
import edu.zipcloud.cloudstreetmarket.core.services.SocialUserService;
import edu.zipcloud.cloudstreetmarket.core.util.UserDetailsUtil;

@Component
@PropertySource("classpath:application.properties")
public class CloudstreetApiWCI extends WebContentInterceptor {

    private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    
    protected static final String LOCATION_HEADER = "Location";
    protected static final String BASIC_TOKEN = "Basic-Token";
    protected static final String MUST_REGISTER_HEADER = "Must-Register";
    private static final String WWW_AUTHENTICATE_HEADER = "WWW-Authenticate";
    private static final String AUTHENTICATED_HEADER = "Authenticated";
    private static final String SPI_HEADER = "Spi";
    private static final String BASIC_SCHEME = "CSM_Basic";
    private static final String OAUTH2_SCHEME = "CSM_OAuth2";
    protected static final String TRUE = "true";
    protected static final String FALSE = "false";
    
    @Value("${realm.name}")
    private String realName = "cloudstreetmarket.com";
    private String authenticationHeaderSequence = " realm=\""+realName+"\"";
    
    @Autowired
	public Environment env;
    
    @Autowired
    private CommunityService communityService;
    
    @Autowired
    private SocialUserService socialUserService;

	public CloudstreetApiWCI(){
		setRequireSession(false);
		setCacheSeconds(0);
		setSupportedMethods(GET,POST, OPTIONS, HEAD, DELETE);
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException {
		super.preHandle(request, response, handler);
		preAuthenticate(request, response);
		setResponseHeaders(response);
		return true;
	}
	
	private void setResponseHeaders(HttpServletResponse response) {
		boolean authenticated = SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
		if(authenticated){
			UserDetails user = getPrincipal();
			if(UserDetailsUtil.hasRole(user, ROLE_OAUTH2)){
				handlePostSignIn(response, OAUTH2_SCHEME);
			}
			else if(UserDetailsUtil.hasRole(user, ROLE_BASIC)){
				handlePostSignIn(response, BASIC_SCHEME);
			}
		}
	}

	private void preAuthenticate(HttpServletRequest request, HttpServletResponse response){
		String oAuthGuid = request.getHeader(SPI_HEADER);
		UserDetails user = getPrincipal();
		if(!StringUtils.isEmpty(oAuthGuid) || UserDetailsUtil.hasRole(user, ROLE_OAUTH2)){
			SocialUser socialUser = socialUserService.getRegisteredSocialUser(oAuthGuid);
			if(socialUser == null){
				response.setHeader(MUST_REGISTER_HEADER, oAuthGuid);
			}
			else{
				communityService.signInUser(communityService.findOne(socialUser.getUserId()));
			}
		}
	}

	@Override
	public void postHandle(
			HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(
			HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
	}
	
	protected void handlePostSignIn(HttpServletResponse response, String scheme){
		response.setHeader(WWW_AUTHENTICATE_HEADER, scheme.concat(authenticationHeaderSequence));
		response.setHeader(AUTHENTICATED_HEADER, TRUE);
	}
	
	protected void handlePostSignIn(HttpServletResponse response, String scheme, String mustRegister){
		handlePostSignIn(response, scheme);
		response.setHeader(MUST_REGISTER_HEADER, mustRegister);
	}

	public UserDetails getPrincipal(){
       SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext != null) {
            Authentication auth = securityContext.getAuthentication();
	        if (auth != null) {
	            Object principal = auth.getPrincipal();
	            if (principal instanceof UserDetails) {
	            	return (UserDetails) principal;
	            }
	        }
        }
        return new User();
	}

	@InitBinder
	public void allowDateBinding ( WebDataBinder binder )
	{
		binder.registerCustomEditor( Date.class, new CustomDateEditor( df, true ));
	}
}
