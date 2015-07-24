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
