package org.springframework.social.connect;

import org.springframework.social.connect.UserProfile;

public class YahooUserProfile extends UserProfile{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7421321109025996500L;

	public YahooUserProfile(String name, String firstName, String lastName,
			String email, String username) {
		super(name, firstName, lastName, email, username);
	}
	
}
