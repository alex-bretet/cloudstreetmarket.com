package edu.zipcloud.cloudstreetmarket.core.util;
import org.springframework.security.core.userdetails.UserDetails;

import edu.zipcloud.cloudstreetmarket.core.enums.Role;

public class UserDetailsUtil {
	
	public static boolean hasRole(UserDetails userDetails, Role role){
		return userDetails.getAuthorities().stream()
			.filter(a -> {
				return a.getAuthority().equals(role.toString());
			})
			.findFirst()
			.isPresent();
	}
}
