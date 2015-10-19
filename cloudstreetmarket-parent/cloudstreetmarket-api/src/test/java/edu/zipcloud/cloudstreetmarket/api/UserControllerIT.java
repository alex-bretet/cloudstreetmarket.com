package edu.zipcloud.cloudstreetmarket.api;

import org.apache.http.HttpStatus;
import org.junit.*;

import static org.junit.Assert.*;
import static com.jayway.restassured.RestAssured.*;

import com.jayway.restassured.response.Response;

import edu.zipcloud.cloudstreetmarket.core.dtos.UserDTO;
import edu.zipcloud.cloudstreetmarket.core.entities.User;
import edu.zipcloud.cloudstreetmarket.core.enums.SupportedCurrency;
import edu.zipcloud.cloudstreetmarket.core.enums.SupportedLanguage;
import edu.zipcloud.cloudstreetmarket.core.tests.AbstractTestApiIT;

public class UserControllerIT extends AbstractTestApiIT{
		
	protected static final String DEFAULT_IMG_PATH = "img/anon.png";
	protected static final String HIDDEN_FIELD = "hidden";
	
	private static User userA;
	private static User userB;
	
	@Before
	public void before(){
		userA = generateUser();
	}
	
	@After
	public void after(){
		deleteUserAsAdmin(userA.getId());
	}

	@Test
	public void create_user_basic_auth(){
        Response response = given()
            .contentType("application/json;charset=UTF-8")
            .accept("application/json")
    		.body(userA)
            .expect().log().ifError()
            .statusCode(HttpStatus.SC_CREATED)
            .when()
            .post(getHost() + CONTEXT_PATH + "/users");
        
        assertNotNull(response.getHeader("Location"));
        UserDTO userADTO = assertUserExists(response.getHeader("Location"));
        assertUserDTOValidForBASIC(userADTO);
	}

	@Test
	public void create_user_basic_auth_ajax(){
		Response response = given()
            .header("X-Requested-With", "XMLHttpRequest")
            .contentType("application/json;charset=UTF-8")
            .accept("application/json")
    		.body(userA)
            .expect().log().ifError()
            .statusCode(HttpStatus.SC_CREATED)
            .when()
            .post(getHost() + CONTEXT_PATH + "/users");
        
        assertNotNull(response.getHeader("Location"));
        UserDTO userADTO = assertUserExists(response.getHeader("Location"));
        assertUserDTOValidForBASIC(userADTO);
	}
	
	@Test
	public void delete_forbidden_to_user(){
		assertCreateUser(userA);
		userB = generateUser();
		assertCreateUser(userB);

		deleteUserFromCredentials(userB.getId(), userA.getUsername(), userA.getPassword());
	}

	private static void assertCreateUser(User user){
        given()
            .contentType("application/json;charset=UTF-8")
            .accept("application/json")
    		.body(user)
            .expect().log().ifError()
            .statusCode(HttpStatus.SC_CREATED)
            .when()
            .post(getHost() + CONTEXT_PATH + "/users");
	}
	
	private static UserDTO assertUserExists(String path){
		Response response = given()
	        .expect().log().ifError()
	        .statusCode(HttpStatus.SC_OK)
	        .when()
	        .get(getHost() + CONTEXT_PATH + path + JSON_SUFFIX);
		
		return deserialize(response.getBody().asString());
	}
	
	private void assertUserDTOValidForBASIC(UserDTO userADTO) {
		assertEquals(userA.getId(), userADTO.getId());
		assertEquals(userA.getLanguage().name(), userADTO.getLanguage());
		assertEquals(HIDDEN_FIELD, userADTO.getEmail());
		assertEquals(HIDDEN_FIELD, userADTO.getPassword());
		assertNull(userA.getBalance());
	}
	
	private static void deleteUserAsAdmin(String userId) {
		deleteUserFromCredentials(userId, getAdminUsername(), getAdminPassword());
	}
	
	private static void deleteUserFromCredentials(String userId, String username, String password) {
		given()
            .header("X-Requested-With", "XMLHttpRequest")
            .auth()
            .basic(username, password)
            .contentType("application/json;charset=UTF-8")
            .accept("application/json")
            .expect().log().ifError()
            .statusCode(HttpStatus.SC_NO_CONTENT)
            .when()
            .delete(getHost() + CONTEXT_PATH + MONITORING_PATH + userId);
	}
	
	private static User generateUser(){
		return new User.Builder()
			.withId(generateUserName())
			.withEmail(generateEmail())
			.withCurrency(SupportedCurrency.GBP)
			.withPassword(generatePassword())
			.withLanguage(SupportedLanguage.EN)
			.withProfileImg(DEFAULT_IMG_PATH)
			.build();
	}
}
