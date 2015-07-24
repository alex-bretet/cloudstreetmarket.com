package edu.zipcloud.cloudstreetmarket.core.services;

import java.util.Properties;

public interface ResourceBundleService {
	Properties getAll();
	String get(String key);
	boolean containsKey(String key);
	String getFormatted(String key, String... arguments);
}
