package edu.zipcloud.cloudstreetmarket.ws.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.rabbitmq.client.AuthenticationFailureException;

import edu.zipcloud.cloudstreetmarket.core.entities.User;
import edu.zipcloud.cloudstreetmarket.core.services.CommunityService;
import edu.zipcloud.cloudstreetmarket.core.services.SocialUserService;

public class AuthenticationTokenProcessingFilter extends UsernamePasswordAuthenticationFilter{

    private static final String AUTHENTICATED = "Authenticated";
    private static final String OAUTH2_SCHEME = "CSM_OAuth2";
    private static final String WWW_AUTHENTICATE_HEADER = "WWW-Authenticate";

    @Value("${realm.name}")
    private String realName = "cloudstreetmarket.com";
    private String authenticationHeaderSequence = " realm=\""+realName+"\"";
    
	@Autowired
	protected SocialUserService usersConnectionRepository;
	
	@Autowired
	protected CommunityService communityService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException{

		if(request instanceof HttpServletRequest){
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			HttpSession session = httpRequest.getSession(false);
			if(session != null && session.getAttribute(AUTHENTICATED) != null && session.getAttribute(WWW_AUTHENTICATE_HEADER) != null){
				if(session.getAttribute(WWW_AUTHENTICATE_HEADER).equals(OAUTH2_SCHEME.concat(authenticationHeaderSequence))){
					String username = (String) session.getAttribute(AUTHENTICATED);
					User user = communityService.findOne(username);
				    Authentication authentication = new UsernamePasswordAuthenticationToken(user.getId(), user.getPassword(), user.getAuthorities());
				    getAuthenticationManager().authenticate(authentication);
				}
			}
			
			chain.doFilter(httpRequest, response);
		}
		else{
			throw new AuthenticationFailureException("Only Http requests are supported!");
		}
	}
}