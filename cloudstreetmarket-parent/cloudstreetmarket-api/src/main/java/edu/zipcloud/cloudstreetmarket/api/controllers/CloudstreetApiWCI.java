package edu.zipcloud.cloudstreetmarket.api.controllers;

import static edu.zipcloud.cloudstreetmarket.core.enums.Role.ROLE_BASIC;
import static edu.zipcloud.cloudstreetmarket.core.enums.Role.ROLE_OAUTH2;
import static edu.zipcloud.cloudstreetmarket.shared.util.Constants.*;
import static javax.ws.rs.HttpMethod.DELETE;
import static javax.ws.rs.HttpMethod.GET;
import static javax.ws.rs.HttpMethod.HEAD;
import static javax.ws.rs.HttpMethod.OPTIONS;
import static javax.ws.rs.HttpMethod.POST;
import static javax.ws.rs.HttpMethod.PUT;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.core.env.Environment;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Identifiable;
import org.springframework.jms.core.MessageCreator;
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
import edu.zipcloud.cloudstreetmarket.core.services.ResourceBundleService;
import edu.zipcloud.cloudstreetmarket.core.services.SocialUserService;
import edu.zipcloud.cloudstreetmarket.core.util.AuthenticationUtil;
import edu.zipcloud.cloudstreetmarket.core.util.UserDetailsUtil;

@Component
public class CloudstreetApiWCI<T extends Identifiable<?>> extends WebContentInterceptor {

    private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    @Value("${realm.name}")
    private String realmName = "cloudstreetmarket.com";
    private String authenticationHeaderSequence = " realm=\""+realmName+"\"";
    
    @Autowired
	public Environment env;
    
	@Autowired
	protected ResourceBundleService bundle;
    
    @Autowired
    protected CommunityService communityService;
    
    @Autowired
    protected SocialUserService usersConnectionRepository;

    @Autowired
    protected PagedResourcesAssembler<T> pagedAssembler;
    
	@Autowired
	protected RabbitTemplate messagingTemplate;
	
	public MessageCreator messageCreator(Serializable payload){
		return new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createObjectMessage(payload);
			}
	    };
	}

	public CloudstreetApiWCI(){
		setCacheSeconds(0);
		setSupportedMethods(GET,POST,PUT, OPTIONS, HEAD, DELETE);
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException {
		super.preHandle(request, response, handler);
		preAuthenticate(request, response);
		setResponseHeaders(request, response);
		return true;
	}
	
	private void setResponseHeaders(HttpServletRequest request,  HttpServletResponse response) {
		boolean authenticated = SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
		if(authenticated){
			UserDetails user = getPrincipal();
			if(UserDetailsUtil.hasRole(user, ROLE_OAUTH2)){
				handlePostSignIn(request, response, OAUTH2_SCHEME);
			}
			else if(UserDetailsUtil.hasRole(user, ROLE_BASIC)){
				handlePostSignIn(request, response, BASIC_SCHEME);
			}
		}
	}
	
	public static boolean isAjax(HttpServletRequest request) {
		return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
	}

	private void preAuthenticate(HttpServletRequest request, HttpServletResponse response){
		String oAuthGuid = request.getHeader(SPI_HEADER);
		UserDetails user = getPrincipal();
		if(!StringUtils.isEmpty(oAuthGuid) || UserDetailsUtil.hasRole(user, ROLE_OAUTH2)){
			SocialUser socialUser = usersConnectionRepository.getRegisteredSocialUser(oAuthGuid);
			if(socialUser == null){
				response.setHeader(MUST_REGISTER_HEADER, oAuthGuid);
			}
			else{
				User registeredUser = communityService.findOne(socialUser.getUserId());
				communityService.signInUser(registeredUser);
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
	
	protected void handlePostSignIn(HttpServletRequest request, HttpServletResponse response, String scheme){
		response.setHeader(WWW_AUTHENTICATE_HEADER, scheme.concat(authenticationHeaderSequence));
		response.setHeader(AUTHENTICATED_HEADER, SecurityContextHolder.getContext().getAuthentication().getName());
		
		HttpSession session = request.getSession(false);
		if(session != null){
			session.setAttribute(WWW_AUTHENTICATE_HEADER, scheme.concat(authenticationHeaderSequence));
			session.setAttribute(AUTHENTICATED_HEADER, SecurityContextHolder.getContext().getAuthentication().getName());
		}
	}
	
	protected void handlePostSignIn(HttpServletRequest request, HttpServletResponse response, String scheme, String mustRegister){
		handlePostSignIn(request, response, scheme);
		response.setHeader(MUST_REGISTER_HEADER, mustRegister);
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

	@InitBinder
	public void allowDateBinding ( WebDataBinder binder )
	{
		binder.registerCustomEditor( Date.class, new CustomDateEditor( df, true ));
	}
	
	@InitBinder
	protected void registerValidator ( WebDataBinder binder){
		//binder.setValidator(new UserValidator());
	};
}
