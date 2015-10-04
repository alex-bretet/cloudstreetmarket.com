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
package edu.zipcloud.cloudstreetmarket.api.exceptions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.http.HttpStatus;

import edu.zipcloud.core.util.ExceptionUtil;

public class ErrorInfo {

    public final String error;
    public final String message;
    public final String i18nKey;
    public final int status;
    public final String date;
	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public String getError() {
		return error;
	}

	public String getMessage() {
		return message;
	}

	public int getStatus() {
		return status;
	}

	public String getDate() {
		return date;
	}

	public String getI18nKey() {
		return i18nKey;
	}

	public ErrorInfo(Throwable throwable, String message, String i18nKey, HttpStatus status) {
    	this.error = ExceptionUtil.getRootMessage(throwable);
    	this.message = message;
    	this.date = dateFormat.format(new Date());
    	this.status = status.value();
    	this.i18nKey = i18nKey;
    }

	@Override
	public String toString() {
		return "ErrorInfo [status="+status+", error=" + error + ", date=" + date + "]";
	}
}