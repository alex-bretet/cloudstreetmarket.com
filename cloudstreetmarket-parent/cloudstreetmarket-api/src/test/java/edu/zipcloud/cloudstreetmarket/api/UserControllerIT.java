package edu.zipcloud.cloudstreetmarket.api;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;

import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jayway.restassured.response.Response;

import edu.zipcloud.cloudstreetmarket.core.dtos.UserDTO;
import edu.zipcloud.cloudstreetmarket.core.entities.User;
import edu.zipcloud.cloudstreetmarket.core.tests.AbstractCommonTestUser;
import edu.zipcloud.core.util.DateUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-context-api-test.xml")
public class UserControllerIT extends AbstractCommonTestUser{

	private static User userA;
	private static User userB;

	@Autowired
    private JdbcTemplate jdbcTemplate;
    
	@Before
	public void before(){
		userA = generateUser();
		userB = generateUser();
	}
	
	@After
	public void after(){
		deleteUserAsAdmin(userA);
	}

	@Test
	public void createUserBasicAuth(){
        Response response = given()
					            .contentType("application/json;charset=UTF-8")
					            .accept("application/json")
					    		.body(userA)
					            .expect().log().ifError()
					            .when()
					            .post(getHost() + CONTEXT_PATH + "/users");
					        
        assertNotNull(response.getHeader("Location"));
        UserDTO userADTO = assertUserExists(response.getHeader("Location"));
        assertUserDTOValidForBASIC(userADTO);
	}

	@Test
	public void createUserBasicAuthAjax(){
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
	public void createUserOAuth2Ajax(){
		String spi = generateSpi();
		insertConnection(spi, userA.getId());
		
		Response response = given()
					            .header("X-Requested-With", "XMLHttpRequest")
					            .header("Spi", spi)
					            .header("OAuthProvider", "yahoo")
					            .contentType("application/json;charset=UTF-8")
					            .accept("application/json")
					    		.body(userA)
					            .expect().log().ifError()
					            .statusCode(HttpStatus.SC_CREATED)
					            .when()
					            .post(getHost() + CONTEXT_PATH + "/users");
					        
        assertNotNull(response.getHeader("Location"));
        UserDTO userADTO = assertUserExists(response.getHeader("Location"));
        assertUserDTOValidForOAuth2(userADTO);
        deleteConnection(spi, userA.getId());
	}

	@Test
	public void updateUserSelfUpdateByBasic(){
		assertCreateUser(userA);

		given()
            .contentType("application/json;charset=UTF-8")
            .accept("application/json")
	        .auth()
	        .preemptive()
	        .basic(userA.getId(), userA.getPassword())
	        .body(userA)
	        .expect().log().ifError()
	        .statusCode(HttpStatus.SC_OK)
	        .when()
	        .put(getHost() + CONTEXT_PATH + USER_CONTROLLER_PATH + userA.getId());
	}
	
	@Test
	public void updateUserSelfUpdateByBasicAjax(){
		assertCreateUser(userA);

		given()
            .header("X-Requested-With", "XMLHttpRequest")
            .contentType("application/json;charset=UTF-8")
            .accept("application/json")
	        .auth()
	        .preemptive()
	        .basic(userA.getId(), userA.getPassword())
	        .body(userA)
	        .expect().log().ifError()
	        .statusCode(HttpStatus.SC_OK)
	        .when()
	        .put(getHost() + CONTEXT_PATH + USER_CONTROLLER_PATH + userA.getId());
	}
	
	@Test
	public void update3rdPartyUserForbiddenForBasicUser(){
		assertCreateUser(userA);
		assertCreateUser(userB);

		given()
            .contentType("application/json;charset=UTF-8")
            .accept("application/json")
	        .auth()
	        .preemptive()
	        .basic(userA.getId(), userA.getPassword())
	        .body(userB)
	        .expect()
	        .statusCode(HttpStatus.SC_FORBIDDEN)
	        .when()
	        .put(getHost() + CONTEXT_PATH + USER_CONTROLLER_PATH + userB.getId());
		
		deleteUserAsAdmin(userB);
	}
	
	@Test
	public void update3rdPartyUserForbiddenForBasicUserAjax(){
		assertCreateUser(userA);
		assertCreateUser(userB);

		given()
            .header("X-Requested-With", "XMLHttpRequest")
            .contentType("application/json;charset=UTF-8")
            .accept("application/json")
	        .auth()
	        .preemptive()
	        .basic(userA.getId(), userA.getPassword())
	        .body(userB)
	        .expect()
	        .statusCode(HttpStatus.SC_FORBIDDEN)
	        .when()
	        .put(getHost() + CONTEXT_PATH + USER_CONTROLLER_PATH + userB.getId());
		
		deleteUserAsAdmin(userB);
	}
	
	private void assertUserDTOValidForBASIC(UserDTO userADTO) {
		assertEquals(userA.getId(), userADTO.getId());
		assertEquals(userA.getLanguage().name(), userADTO.getLanguage());
		assertEquals(HIDDEN_FIELD, userADTO.getEmail());
		assertEquals(HIDDEN_FIELD, userADTO.getPassword());
		assertNull(userA.getBalance());
	}
	
	private void assertUserDTOValidForOAuth2(UserDTO userADTO) {
		assertEquals(userA.getId(), userADTO.getId());
		assertEquals(userA.getLanguage().name(), userADTO.getLanguage());
		assertEquals(HIDDEN_FIELD, userADTO.getEmail());
		assertEquals(HIDDEN_FIELD, userADTO.getPassword());
		assertEquals(20000, userADTO.getBalance().intValue());
	}
	
	public void insertConnection(String spi, String username) {
        this.jdbcTemplate.execute("insert into userconnection ("
        		+ "accessToken, createDate, displayName, expireTime, "
        		+ "imageUrl, last_update, profileUrl, providerId, providerUserId, "
        		+ "rank, refreshToken, secret, userId "
        		+ ") values ("
        		+ "'"+generateGuid()+"', '"+formatDate(new Date())+"', NULL, '"+DateUtil.getXMinAfterDate(new Date(), 500).getTime()+"', "
        		+ "NULL, '"+formatDate(new Date())+"', NULL, 'yahoo', '"+spi+"', "
        		+ "'0', '"+generateGuid()+"', NULL, '"+spi+"');");
    }
	
	private void deleteConnection(String spi, String id) {
        this.jdbcTemplate.execute("delete from userconnection where providerUserId = '"+spi+"' and userId = '"+id+"'");
	}
}
