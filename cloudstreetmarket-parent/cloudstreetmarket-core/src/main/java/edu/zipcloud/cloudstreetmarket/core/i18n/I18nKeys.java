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
package edu.zipcloud.cloudstreetmarket.core.i18n;

public class I18nKeys {

	//Messages
	public static final String I18N_ACTION_REGISTERS = "webapp.action.feeds.action.registers";
	public static final String I18N_ACTION_BUYS = "webapp.action.feeds.action.buys";
	public static final String I18N_ACTION_SELLS = "webapp.action.feeds.action.sells";
	public static final String I18N_ACTION_LIKES = "webapp.action.feeds.action.likes";
	public static final String I18N_ACTION_FOLLOWS = "webapp.action.feeds.action.follows";
	public static final String I18N_ACTION_SEES = "webapp.action.feeds.action.sees";
	public static final String I18N_ACTION_COMMENTS = "webapp.action.feeds.action.comments";
	
	//Errors
	public static final String I18N_USER_GUID_UNKNOWN_TO_PROVIDER = "error.api.user.control.guid.unknown.to.provider";
	public static final String I18N_TRANSACTIONS_USER_FORBIDDEN = "error.api.transactions.control.wrong.user";
	public static final String I18N_TRANSACTIONS_CANT_AFFORD = "error.api.transactions.control.cant.afford";
	public static final String I18N_TRANSACTIONS_DONT_OWN_QUANTITY = "error.api.transactions.control.dont.own.quantity";
	
	public static final String I18N_INDICES_INDEX_NOT_FOUND = "error.api.indices.index.not.found";

	public static final String I18N_API_GENERIC_REQUEST_BODY_NOT_VALID = "error.api.generic.provided.request.body.not.valid";
	public static final String I18N_API_GENERIC_REQUEST_PARAMS_NOT_VALID = "error.api.generic.provided.request.parameters.not.valid";
	public static final String I18N_API_GENERIC_OPERATION_DENIED = "error.api.generic.operation.denied";
	public static final String I18N_API_GENERIC_RESOURCE_NOT_FOUND = "error.api.generic.request.resource.not.found";
	public static final String I18N_API_GENERIC_RESOURCE_ALREADY_EXISTS = "error.api.generic.resource.already.exists";
	public static final String I18N_API_GENERIC_INTERNAL = "error.api.generic.internal";
	
	
}
