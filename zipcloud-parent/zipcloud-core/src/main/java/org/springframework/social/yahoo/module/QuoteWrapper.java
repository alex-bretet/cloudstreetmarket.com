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

import java.util.ArrayList;
import java.util.Collection;

public class QuoteWrapper extends ArrayList<YahooQuote> {
	
	private static final long serialVersionUID = -3924648737519678218L;

	public QuoteWrapper() {
    }

    public QuoteWrapper(Collection<? extends YahooQuote> c) {
        super(c);
    }

	@Override
	public String toString() {
		return "QuoteWrapper [" + super.toString() + "]";
	}

}
