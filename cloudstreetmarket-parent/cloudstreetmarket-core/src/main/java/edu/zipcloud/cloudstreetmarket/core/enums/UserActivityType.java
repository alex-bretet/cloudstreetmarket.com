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

import java.io.Serializable;
import static edu.zipcloud.cloudstreetmarket.core.i18n.I18nKeys.*;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum UserActivityType implements Serializable{
	
	REGISTER(I18N_ACTION_REGISTERS), 
	BUY(I18N_ACTION_BUYS), 
	SELL(I18N_ACTION_SELLS), 
	LIKE(I18N_ACTION_LIKES), 
	FOLLOW(I18N_ACTION_FOLLOWS),
	SEE(I18N_ACTION_SEES),
	COMMENT(I18N_ACTION_COMMENTS);
	
	private String presentTense;
	
	private UserActivityType(String present){
		this.presentTense = present;
	}
	
	public String getPresentTense(){
		return presentTense;
	}
	
	public String getType(){
		return this.name();
	}
}
