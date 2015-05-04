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
