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
package edu.zipcloud.cloudstreetmarket.shared.util;

public class Constants {
	
	public static final String JMS_USER_ACTIVITY_QUEUE = "JMS_USER_ACTIVITY";
	public static final String WS_TOPIC_ACTIVITY_FEED_PATH = "/topic/actions";
	public static final String ACTIVITY_FEED_ENDPOINT = "/channels/users/broadcast";
	public static final String PRIVATE_STOCKS_ENDPOINT = "/channels/private";
	public static final String SOCKJS_CLIENT_LIB = "//cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.0.3/sockjs.js";
	
    public static final String LOCATION_HEADER = "Location";
    public static final String BASIC_TOKEN = "Basic-Token";
    public static final String MUST_REGISTER_HEADER = "Must-Register";
    public static final String WWW_AUTHENTICATE_HEADER = "WWW-Authenticate";
    public static final String AUTHENTICATED_HEADER = "Authenticated";
    public static final String SPI_HEADER = "Spi";
    public static final String BASIC_SCHEME = "CSM_Basic";
    public static final String OAUTH2_SCHEME = "CSM_OAuth2";
    public static final String TRUE = "true";
    public static final String FALSE = "false";
	
}
