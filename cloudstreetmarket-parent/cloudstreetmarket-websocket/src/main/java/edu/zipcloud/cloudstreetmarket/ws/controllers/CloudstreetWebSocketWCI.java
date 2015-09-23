package edu.zipcloud.cloudstreetmarket.ws.controllers;

import static javax.ws.rs.HttpMethod.DELETE;
import static javax.ws.rs.HttpMethod.GET;
import static javax.ws.rs.HttpMethod.HEAD;
import static javax.ws.rs.HttpMethod.OPTIONS;
import static javax.ws.rs.HttpMethod.POST;
import static javax.ws.rs.HttpMethod.PUT;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Identifiable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

import edu.zipcloud.cloudstreetmarket.core.entities.User;
import edu.zipcloud.cloudstreetmarket.core.services.CommunityService;
import edu.zipcloud.cloudstreetmarket.core.services.SocialUserService;
import edu.zipcloud.cloudstreetmarket.core.util.AuthenticationUtil;

@Component
public class CloudstreetWebSocketWCI<T extends Identifiable<?>> extends WebContentInterceptor {

    @Autowired
    protected SocialUserService usersConnectionRepository;
    
    @Autowired
	public Environment env;

    @Autowired
    protected PagedResourcesAssembler<T> pagedAssembler;
    
    @Autowired
    protected CommunityService communityService;
    
	@Autowired
	protected SimpMessagingTemplate messagingTemplate;
	
	public CloudstreetWebSocketWCI(){
		setCacheSeconds(0);
		setSupportedMethods(GET,POST,PUT, OPTIONS, HEAD, DELETE);
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException {
		super.preHandle(request, response, handler);
		return true;
	}

	@Override
	public void postHandle(
			HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(
			HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
	}

	public UserDetails getPrincipal(){
	   return AuthenticationUtil.getPrincipal();
	}
	
	public User getAuthenticated(){
		UserDetails userDetail = getPrincipal();
		if(userDetail != null){
			return communityService.findByLogin(userDetail.getUsername());
		}
		return null;
	}
}
