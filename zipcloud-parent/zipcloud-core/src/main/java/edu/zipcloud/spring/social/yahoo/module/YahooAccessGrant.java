package edu.zipcloud.spring.social.yahoo.module;

import org.springframework.social.oauth2.AccessGrant;

public class YahooAccessGrant extends AccessGrant{

	private static final long serialVersionUID = -9063195821726881620L;

	private final String xoauthYahooGuid;
	
	public YahooAccessGrant(String accessToken) {
		this(accessToken, null, null, null, null);
	}

	public YahooAccessGrant(String accessToken, String scope, String refreshToken, Long expiresIn, String xoauthYahooGuid) {
		super(accessToken, scope, refreshToken, 
				expiresIn != null ? System.currentTimeMillis() + expiresIn * 1000l : null);
		this.xoauthYahooGuid = xoauthYahooGuid;
	}

	public String getXoauthYahooGuid() {
		return xoauthYahooGuid;
	}

}
