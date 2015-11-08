/***
 *  Cloudstreetmarket.com is a Spring MVC showcase application developed 
 *  with the book Spring MVC Cookbook [PACKT] (2015). 
 * 	Copyright (C) 2015  Alex Bretet
 *  
 *  This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 **/
package edu.zipcloud.cloudstreetmarket.core.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import edu.zipcloud.cloudstreetmarket.core.entities.User;
import edu.zipcloud.cloudstreetmarket.core.enums.Role;

public class AuthenticationUtil {
	
	public static boolean userHasRole(Role role){
	    SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext != null) {
            Authentication auth = securityContext.getAuthentication();
	        if (auth != null) {
	    		if(!auth.isAuthenticated()){
	    			return false;
	    		}
	        }
        }
		
		return UserDetailsUtil.hasRole(getPrincipal(), role);
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
	
	public static boolean isThePrincipal(String userId){
	       SecurityContext securityContext = SecurityContextHolder.getContext();
	        if (securityContext != null && !StringUtils.isBlank(userId)) {
	            Authentication auth = securityContext.getAuthentication();
		        if (auth != null) {
		            Object principal = auth.getPrincipal();
		            if (principal instanceof UserDetails && userId.equals(((UserDetails) principal).getUsername())) {
		            	return true;
		            }
		        }
	        }
	        return false;
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
