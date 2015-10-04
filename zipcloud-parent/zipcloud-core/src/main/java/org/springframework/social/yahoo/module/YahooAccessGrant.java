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
package org.springframework.social.yahoo.module;

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
