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
package org.springframework.social.yahoo.connect;

import java.util.Map;

import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.social.yahoo.module.YahooAccessGrant;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
* Yahoo OAuth2Template
* @author Alex Bretet
* @author Michael Lavelle
*/
public class YahooOAuth2Template extends OAuth2Template {

	private String redirectUri;
	private String clientId;
	private String clientSecret;
	public static final String ACCESS_TOKEN_URL = "https://api.login.yahoo.com/oauth2/get_token";
	public static final String AUTH_URL = "https://api.login.yahoo.com/oauth2/request_auth";

	public YahooOAuth2Template(String clientId, String clientSecret) {
		this(clientId, clientSecret, null);
	}

	public YahooOAuth2Template(String clientId, String clientSecret,String redirectUri) {
		super(clientId, clientSecret, AUTH_URL, ACCESS_TOKEN_URL);
		this.redirectUri = redirectUri;
	}
	
	@Override
	public String buildAuthenticateUrl(GrantType grantType, OAuth2Parameters parameters) {
		if (redirectUri != null) parameters.setRedirectUri(redirectUri);
		return super.buildAuthenticateUrl(grantType, parameters);
	}

	@Override
	public String buildAuthorizeUrl(GrantType grantType, OAuth2Parameters parameters) {
		if (redirectUri != null) parameters.setRedirectUri(redirectUri);
		return super.buildAuthorizeUrl(grantType, parameters);
	}

	@Override
	public YahooAccessGrant exchangeForAccess(String authorizationCode, String redirectUri, MultiValueMap<String, String> additionalParameters) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.set("client_id", clientId);
		params.set("client_secret", clientSecret);
		params.set("code", authorizationCode);
		params.set("redirect_uri", this.redirectUri != null ? this.redirectUri : redirectUri);
		params.set("grant_type", "authorization_code");
		if (additionalParameters != null) {
			params.putAll(additionalParameters);
		}
		return postForAccessGrant(ACCESS_TOKEN_URL, params);
	}
	
	protected YahooAccessGrant createAccessGrant(String accessToken, String scope, String refreshToken, Long expiresIn, String guid, Map<String, Object> response) {
		return new YahooAccessGrant(accessToken, scope, refreshToken, expiresIn, guid);
	}
	
	@SuppressWarnings("unchecked")
	protected YahooAccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
		return extractAccessGrant(getRestTemplate().postForObject(accessTokenUrl, parameters, Map.class));
	}
	
	private YahooAccessGrant extractAccessGrant(Map<String, Object> result) {
		return createAccessGrant((String) result.get("access_token"), (String) result.get("scope"), (String) result.get("refresh_token"), getIntegerValue(result, "expires_in"), (String) result.get("xoauth_yahoo_guid"), result);
	}
	
	private Long getIntegerValue(Map<String, Object> map, String key) {
		try {
			return Long.valueOf(String.valueOf(map.get(key))); // normalize to String before creating integer value;			
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
}