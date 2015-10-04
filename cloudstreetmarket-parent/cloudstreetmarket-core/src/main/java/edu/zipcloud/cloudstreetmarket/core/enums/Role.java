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
package edu.zipcloud.cloudstreetmarket.core.enums;

public enum Role {
	
	ROLE_ANONYMOUS,
	ROLE_BASIC,
	ROLE_OAUTH2,
	ROLE_ADMIN,
	ROLE_SYSTEM,
	IS_AUTHENTICATED_REMEMBERED; //Transitory role
	
	public static final String ANONYMOUS = "ROLE_ANONYMOUS";
	public static final String BASIC = "ROLE_BASIC";
	public static final String OAUTH2 = "ROLE_OAUTH2";
	public static final String ADMIN = "ROLE_ADMIN";
	public static final String SYSTEM = "ROLE_SYSTEM";
}
