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
package edu.zipcloud.cloudstreetmarket.core.services;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zipcloud.cloudstreetmarket.core.i18n.SerializableResourceBundleMessageSource;
import edu.zipcloud.cloudstreetmarket.core.util.AuthenticationUtil;

@Service
@Transactional(readOnly = true)
public class ResourceBundleServiceImpl implements ResourceBundleService {

	@Autowired
	protected SerializableResourceBundleMessageSource messageBundle;
	
	private static final Map<Locale, Properties> localizedMap = new HashMap<>();
	
	@Override
	public Properties getAll() {
    	return getBundleForUser();
	}

	@Override
	public String get(String key) {
		return getBundleForUser().getProperty(key);
	}
	
	@Override
	public String getFormatted(String key, String... arguments) {
		return MessageFormat.format(getBundleForUser().getProperty(key), arguments);
	}
	
	@Override
	public boolean containsKey(String key) {
		return getAll().containsKey(key);
	}
	
	private Properties getBundleForUser(){
		Locale locale = AuthenticationUtil.getUserPrincipal().getLocale();
    	if(!localizedMap.containsKey(locale)){
    		localizedMap.put(locale, messageBundle.getAllProperties(locale));
    	}
    	return localizedMap.get(locale);
	}
}
