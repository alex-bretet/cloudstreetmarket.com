package edu.zipcloud.cloudstreetmarket.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.NativeWebRequest;

import edu.zipcloud.cloudstreetmarket.core.entities.User;
import edu.zipcloud.cloudstreetmarket.core.services.CommunityService;

/**
 * Controller for handling the sign up (aka registration) process.
 *
 * Also handles when a new user signs in via Spring Social. The
 * Spring Social ProviderSignInController will redirect the new
 * user to the GET handler. In this app, a local account will be
 * automatically created for the new user and they will be signed
 * in to the Spring Security SecurityContext.
 */
@Controller
@RequestMapping("/signup")
@PropertySource("classpath:application.properties")
public class SignUpController extends CloudstreetApiWCI{

    @Autowired
    private CommunityService communityService;
    
    @Autowired
    private SignInAdapter signInAdapter;
    
    @Autowired
    private ConnectionRepository connectionRepository;
    
	@Value("${oauth.signup.success.view}")
    private String successView;

    @RequestMapping(method = RequestMethod.GET)
    public String getForm(NativeWebRequest request,  @ModelAttribute User user) {
    	String view = successView;
    	
        // check if this is a new user signing in via Spring Social
        Connection<?> connection = ProviderSignInUtils.getConnection(request);
        if (connection != null) {
            // populate new User from social connection user profile
            UserProfile userProfile = connection.fetchUserProfile();
            user.setUsername(userProfile.getUsername());

            // finish social signup/login
            ProviderSignInUtils.handlePostSignUp(user.getUsername(), request);

            // sign the user in and send them to the user home page
            signInAdapter.signIn(user.getUsername(), connection, request);
    		view += "?spi="+ user.getUsername();
        }
        return view;
    }
}