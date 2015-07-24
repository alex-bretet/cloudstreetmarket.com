package edu.zipcloud.cloudstreetmarket.core.util;

import static edu.zipcloud.cloudstreetmarket.core.enums.Role.*;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import edu.zipcloud.cloudstreetmarket.core.entities.User;
import edu.zipcloud.cloudstreetmarket.core.enums.Role;

public class AuthenticationUtil {
	
	public static boolean userHasRole(Role role){
		boolean authenticated = SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
		if(!authenticated){
			return false;
		}
		return UserDetailsUtil.hasRole(getPrincipal(), ROLE_OAUTH2);
	}
	
	public static UserDetails getPrincipal(){
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
	
	public static User getUserPrincipal(){
	       SecurityContext securityContext = SecurityContextHolder.getContext();
	        if (securityContext != null) {
	            Authentication auth = securityContext.getAuthentication();
		        if (auth != null) {
		            Object principal = auth.getPrincipal();
		            if (principal instanceof User) {
		            	return (User) principal;
		            }
		        }
	        }
	        return new User();
	}
}
