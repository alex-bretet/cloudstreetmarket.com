package edu.zipcloud.cloudstreetmarket.shared.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.zipcloud.cloudstreetmarket.core.services.SocialUserServiceImpl;


@Component
public class OfflineUtil {
	
	@Autowired
	private SocialUserServiceImpl SocialUserImpl;
	
}
