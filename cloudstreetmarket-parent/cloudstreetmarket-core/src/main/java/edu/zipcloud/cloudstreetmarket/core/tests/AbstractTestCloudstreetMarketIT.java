package edu.zipcloud.cloudstreetmarket.core.tests;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.zipcloud.cloudstreetmarket.core.dtos.UserDTO;

public abstract class AbstractTestCloudstreetMarketIT {

	private static Properties appProperties = new Properties();
	protected static ObjectMapper mapper = new ObjectMapper();
	
	{
		try {
			appProperties.load(new FileReader(new File(System.getProperty("user.home") + "/app/cloudstreetmarket.properties")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public static String getProperty(String key){
		String systemProperty = System.getProperty(key);
		if(StringUtils.isBlank(systemProperty)){
			return appProperties.getProperty(key);
		}
		return systemProperty;
	}
	
	public static String getHost(){
		return getProperty("configured.protocol").concat(getProperty("realm.name"));
	}
	
	public static String generateUserName(){
		return "it_test" + System.currentTimeMillis();
	}
	
	public static String generateEmail(){
		return "it_test" + System.currentTimeMillis() +"@csm.com";
	}
	
	public static String getAdminUsername(){
		return getProperty("username.admin");
	}
	
	public static String getAdminPassword(){
		return getProperty("password.admin");
	}
	
	public static String generatePassword(){
		return String.valueOf(System.currentTimeMillis());
	}
	
	public static String serialize(Object object){
		if(mapper.canSerialize(object.getClass())){
			try {
				return mapper.writeValueAsString(object);
			} catch (JsonProcessingException e) {
				throw new IllegalArgumentException("Unserializable " + object.getClass().getName() +" -> " + object.toString());
			}
		}
		return null;
	}
	
	public static UserDTO deserialize(String object){
		try {
			return mapper.readValue(object, UserDTO.class);
		} catch (IOException e) {
			throw new IllegalArgumentException("Unserializable " + object.getClass().getName() +" -> " + object.toString());
		}
	}
	
}
