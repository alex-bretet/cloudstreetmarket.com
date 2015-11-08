package edu.zipcloud.cloudstreetmarket.api;

import static com.jayway.restassured.RestAssured.given;

import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.zipcloud.cloudstreetmarket.core.entities.User;
import edu.zipcloud.cloudstreetmarket.core.tests.AbstractCommonTestUser;

public class MonitoringControllerIT extends AbstractCommonTestUser{
	
	private static User userA;
	private static User userB;
	
	@Before
	public void before(){
		userA = generateUser();
		userB = generateUser();
	}
	
	@After
	public void after(){
		deleteUserAsAdmin(userA);
		deleteUserAsAdmin(userB);
	}
	
	@Test
	public void deleteUserForbiddenToPublic(){
		assertCreateUser(userA);
		assertCreateUser(userB);

		given()
	        .header("X-Requested-With", "XMLHttpRequest")
	        .contentType("application/json;charset=UTF-8")
	        .accept("application/json")
	        .expect()
	        .statusCode(HttpStatus.SC_FORBIDDEN)
	        .when()
	        .delete(getHost() + CONTEXT_PATH + MONITORING_PATH + userB.getId());
	}
	
	@Test
	public void deleteUserForbiddenToBasicUser(){
		assertCreateUser(userA);
		assertCreateUser(userB);

		given()
	        .header("X-Requested-With", "XMLHttpRequest")
	        .auth()
	        .preemptive()
	        .basic(userA.getId(), userA.getPassword())
	        .contentType("application/json;charset=UTF-8")
	        .accept("application/json")
	        .expect()
	        .statusCode(HttpStatus.SC_FORBIDDEN)
	        .when()
	        .delete(getHost() + CONTEXT_PATH + MONITORING_PATH + userB.getId());
	}
	
	@Test
	public void deleteUserAuthorizedToAdmin(){
		assertCreateUser(userB);

		given()
	        .header("X-Requested-With", "XMLHttpRequest")
	        .auth()
	        .preemptive()
	        .basic(getAdminUsername(), getAdminPassword())
	        .contentType("application/json;charset=UTF-8")
	        .accept("application/json")
	        .expect()
	        .statusCode(HttpStatus.SC_NO_CONTENT)
	        .when()
	        .delete(getHost() + CONTEXT_PATH + MONITORING_PATH + userB.getId());
	}
}
