package edu.zipcloud.cloudstreetmarket.core.tests;

import static com.jayway.restassured.RestAssured.given;

import java.util.UUID;

import org.apache.http.HttpStatus;

import com.jayway.restassured.response.Response;

import edu.zipcloud.cloudstreetmarket.core.dtos.UserDTO;
import edu.zipcloud.cloudstreetmarket.core.entities.User;
import edu.zipcloud.cloudstreetmarket.core.enums.SupportedCurrency;
import edu.zipcloud.cloudstreetmarket.core.enums.SupportedLanguage;

public abstract class AbstractCommonTestUser extends AbstractTestCloudstreetMarket {
	
	protected static final String CONTEXT_PATH = "/api";
	protected static final String MONITORING_PATH = "/monitoring/users/";
	protected static final String USER_CONTROLLER_PATH = "/users/";
	protected static final String JSON_SUFFIX = ".json";
	protected static final String DEFAULT_IMG_PATH = "img/anon.png";
	protected static final String HIDDEN_FIELD = "hidden";

	protected static void assertCreateUser(User user){
        given()
            .contentType("application/json;charset=UTF-8")
            .accept("application/json")
    		.body(user)
            .expect().log().ifError()
            .statusCode(HttpStatus.SC_CREATED)
            .when()
            .post(getHost() + CONTEXT_PATH + "/users");
	}
	
	protected static void deleteUserAsAdmin(User user) {
		if(user!=null){
			deleteUserFromCredentials(user.getId(), getAdminUsername(), getAdminPassword());
		}
	}
	
	protected static void deleteUserFromCredentials(String userId, String username, String password) {
		given()
            .header("X-Requested-With", "XMLHttpRequest")
	        .auth()
	        .preemptive()
	        .basic(username, password)
            .contentType("application/json;charset=UTF-8")
            .accept("application/json")
            .expect()
            .when()
            .delete(getHost() + CONTEXT_PATH + MONITORING_PATH + userId);
	}
	
	protected static User generateUser(){
		return new User.Builder()
			.withId(generateUserName())
			.withEmail(generateEmail())
			.withCurrency(SupportedCurrency.USD)
			.withPassword(generatePassword())
			.withLanguage(SupportedLanguage.EN)
			.withProfileImg(DEFAULT_IMG_PATH)
			.build();
	}
	
	protected static String generateSpi(){
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	protected static String generateGuid(){
		return UUID.randomUUID().toString();
	}

	protected static UserDTO assertUserExists(String path){
		Response response = given()
	        .expect().log().ifError()
	        .statusCode(HttpStatus.SC_OK)
	        .when()
	        .get(getHost() + CONTEXT_PATH + path + JSON_SUFFIX);
		
		return deserialize(response.getBody().asString());
	}
}
