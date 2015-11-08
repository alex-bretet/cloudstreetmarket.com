package edu.zipcloud.cloudstreetmarket.core.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.yahoo.module.YahooQuote;
import org.springframework.web.client.ResourceAccessException;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import edu.zipcloud.cloudstreetmarket.core.daos.ActionRepository;
import edu.zipcloud.cloudstreetmarket.core.daos.UserRepository;
import edu.zipcloud.cloudstreetmarket.core.dtos.UserActivityDTO;
import edu.zipcloud.cloudstreetmarket.core.dtos.UserDTO;
import edu.zipcloud.cloudstreetmarket.core.entities.Action;
import edu.zipcloud.cloudstreetmarket.core.entities.Authority;
import edu.zipcloud.cloudstreetmarket.core.entities.CommentAction;
import edu.zipcloud.cloudstreetmarket.core.entities.CurrencyExchange;
import edu.zipcloud.cloudstreetmarket.core.entities.LikeAction;
import edu.zipcloud.cloudstreetmarket.core.entities.StockProduct;
import edu.zipcloud.cloudstreetmarket.core.entities.StockQuote;
import edu.zipcloud.cloudstreetmarket.core.entities.Transaction;
import edu.zipcloud.cloudstreetmarket.core.entities.User;
import edu.zipcloud.cloudstreetmarket.core.enums.Role;
import edu.zipcloud.cloudstreetmarket.core.enums.SupportedCurrency;
import edu.zipcloud.cloudstreetmarket.core.enums.SupportedLanguage;
import edu.zipcloud.cloudstreetmarket.core.enums.UserActivityType;
import edu.zipcloud.cloudstreetmarket.core.helpers.CommunityServiceHelper;
import edu.zipcloud.cloudstreetmarket.core.specifications.UserSpecifications;
import edu.zipcloud.cloudstreetmarket.core.util.AuthenticationUtil;

@RunWith(MockitoJUnitRunner.class)
public class CommunityServiceImplTest {

	@Mock
	private ActionRepository actionRepository;
	
	@Mock
	private UserRepository userRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private CommunityServiceHelper communityServiceHelper;
	
	@Mock
	AuthenticationUtil authenticationUtil;
	
	@Mock
	private Authentication authentication;
	
	@Mock
	private SecurityContext securityContext;
	
	@InjectMocks
    private CommunityServiceImpl communityServiceImpl;
	
	private User userA;
	private User userAStored;
	private User userB;
	private User userAdmin;
	
	private Transaction transaction;
	private CommentAction commentAction;
	private StockQuote stockQuote;
	private StockQuote stockQuote2;

	private YahooQuote yahooQuote;
	private YahooQuote yahooQuote2;

	private StockProduct stockProduct;
	private LikeAction likeAction;
	private List<Action> actionList;
	private List<User> userList;
	
	private Pageable actionPageable;
	private Pageable userPageable;
	private Page<Action> actionResultsPage;
	private Page<User> userResultsPage;
	private UserSpecifications<User> userSpecifications;
	private CurrencyExchange currencyExchange;
	
	private static String TICKER_ID = "FB";
	private static String USER_A_NAME = "nameA";
	private static String USER_A_EMAIL = "a@a.com";
	private static String USER_A_PASSWORD = "123PWD";
	private static String USER_A_PASSWORD_ENCODED = "*/&*(";
	private static BigDecimal USER_A_BALANCE = new BigDecimal(15000);
	private static String USER_B_NAME = "nameB";
	private static String USER_B_PASSWORD = "123PWD2";
	private static String USER_B_PASSWORD_ENCODED = "*%^!~";
	private static String USER_ADMIN_NAME = "nameAdmin";
	private static String USER_ADMIN_PASSWORD = "123PWD2";
	private static String USER_ADMIN_PASSWORD_ENCODED = "*%^!~";
	
	private static String HIDDEN_USER_ATTRIBUTE = "hidden";
	private static String COMMENT_1 = "blah1";
	private static Date TRANSACTION_DATE1 = new Date();
	private static Set<Authority> ADMIN_AUTHORITIES = Sets.newHashSet(new Authority(Role.ADMIN));
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		Set<Authority> authorities = Sets.newHashSet(new Authority(Role.OAUTH2), new Authority(Role.BASIC));
		
		//init (reinit) variables
		userA = new User.Builder()
							.withId(USER_A_NAME)
							.withPassword(USER_A_PASSWORD)
							.withLanguage(SupportedLanguage.EN)
							.withEmail(USER_A_EMAIL)
							.withAuthorities(authorities)
							.withBalance(USER_A_BALANCE)
							.withCurrency(SupportedCurrency.USD)
							.build();
		
		userAStored = new User.Builder()
			.withId(USER_A_NAME)
			.withPassword(USER_A_PASSWORD_ENCODED)
			.withLanguage(SupportedLanguage.EN)
			.withBalance(USER_A_BALANCE)
			.withCurrency(SupportedCurrency.USD)
			.withAuthorities(authorities)
			.build();
		
		userB = new User.Builder()
							.withId(USER_B_NAME)
							.withPassword(USER_B_PASSWORD)
							.withLanguage(SupportedLanguage.EN)
							.build();
		
		userAdmin = new User.Builder()
			.withId(USER_ADMIN_NAME)
			.withPassword(USER_ADMIN_PASSWORD)
			.withLanguage(SupportedLanguage.EN)
			.withAuthorities(ADMIN_AUTHORITIES)
			.build();
						
		yahooQuote = new YahooQuote.Builder()
							.withId(TICKER_ID)
							.withCurrency("USD")
							.withBid(25)
							.build();
		
		yahooQuote2 = new YahooQuote.Builder()
						.withId(TICKER_ID)
						.withCurrency("GBP")
						.withBid(20)
						.build();
		
		stockProduct = new StockProduct(TICKER_ID);
		
		stockQuote = new StockQuote(yahooQuote, stockProduct);
		stockQuote2 = new StockQuote(yahooQuote2, stockProduct);

		transaction = new Transaction.Builder()
							.withDate(TRANSACTION_DATE1).withQuantity(3)
							.withUser(userA).withType(UserActivityType.BUY)
							.withStockQuote(stockQuote).build();

		commentAction = new CommentAction.Builder()
								.withComment(COMMENT_1).withUser(userB)
								.withTargetAction(transaction).build();
		
		likeAction = new LikeAction.Builder()
							.withUser(userB).withTargetAction(transaction).build();
		
		actionList = Lists.newArrayList(transaction, commentAction, likeAction);
		actionPageable = new PageRequest(0, 10, Direction.DESC, "name");
		actionResultsPage = new PageImpl<>(actionList, actionPageable, actionList.size());
		
		userList = Lists.newArrayList(userA, userB);
		userPageable = new PageRequest(0, 10, Direction.DESC, "name");
		userResultsPage = new PageImpl<>(userList, userPageable, userList.size());
		userSpecifications = new UserSpecifications<User>();
		
		currencyExchange = new CurrencyExchange.Builder()
									.withId("GBPUSD=X")
									.withDailyLatestValue(new BigDecimal(1.5435))
									.build();
		
		SecurityContextHolder.setContext(securityContext);
	}
	
	@Test
	public void getPublicActivity(){
		when(actionRepository.findAll(actionPageable)).thenReturn(actionResultsPage);
		
		Page<UserActivityDTO> methodResults = communityServiceImpl.getPublicActivity(actionPageable);
		assertEquals(actionList.size(), methodResults.getTotalElements());
		assertEquals(countPublicActivitiesIn(actionList), methodResults.getNumberOfElements());
		verify(actionRepository, times(1)).findAll(actionPageable);
	}

	@Test
	public void findByUserName() {
		when(userRepository.findOne(USER_A_NAME)).thenReturn(userA);
		User user = communityServiceImpl.findByUserName(USER_A_NAME);
		assertEquals(userA, user);
		verify(userRepository, times(1)).findOne(USER_A_NAME);
	}
	
	@Test
	public void createUser_usesDAO() {
		when(userRepository.save(userA)).thenReturn(userA);
		User user = communityServiceImpl.createUser(userA, Role.ROLE_BASIC);
		assertEquals(userA, user);
		verify(userRepository, times(1)).save(userA);
	}
	
	@Test
	public void createUser_setAttributes() {
		when(userRepository.save(userB)).thenReturn(userB);
		when(passwordEncoder.encode(USER_B_PASSWORD)).thenReturn(USER_B_PASSWORD_ENCODED);
		User user = communityServiceImpl.createUser(userB, Role.ROLE_BASIC);
		assertEquals(1, user.getAuthorities().size());
		assertEquals(Role.ROLE_BASIC.name(), user.getAuthorities().iterator().next().getAuthority());
		assertEquals(1, user.getActions().size());
		assertEquals(UserActivityType.REGISTER, user.getActions().iterator().next().getType());
		assertEquals(USER_B_PASSWORD_ENCODED, user.getPassword());
		verify(passwordEncoder, times(1)).encode(USER_B_PASSWORD);
	}
	
	@Test(expected=ConstraintViolationException.class)
	public void createUser_throwsForConflict(){
		when(userRepository.save(userA)).thenReturn(userA);
		when(userRepository.findOne(USER_A_NAME)).thenThrow(new ConstraintViolationException("", null, null));
		communityServiceImpl.createUser(userA, Role.ROLE_BASIC);
	}
	
	@Test
	public void createUser_byRoles_usesDAO() {
		when(userRepository.save(userA)).thenReturn(userA);
		Role[] roles = {Role.ROLE_BASIC, Role.ROLE_OAUTH2};
		User user = communityServiceImpl.createUser(userA, roles);
		assertEquals(userA, user);
		verify(userRepository, times(1)).save(userA);
	}
	
	@Test
	public void createUser_byRoles_setAttributes() {
		when(userRepository.save(userB)).thenReturn(userB);
		when(passwordEncoder.encode(USER_B_PASSWORD)).thenReturn(USER_B_PASSWORD_ENCODED);
		Role[] roles = {Role.ROLE_BASIC, Role.ROLE_OAUTH2};
		User user = communityServiceImpl.createUser(userB, roles);
		assertEquals(2, user.getAuthorities().size());
		Iterator<? extends GrantedAuthority> authoritiesIterator = user.getAuthorities().iterator();
		assertEquals(Role.ROLE_BASIC.name(), authoritiesIterator.next().getAuthority());
		assertEquals(Role.ROLE_OAUTH2.name(), authoritiesIterator.next().getAuthority());
		assertEquals(1, user.getActions().size());
		assertEquals(UserActivityType.REGISTER, user.getActions().iterator().next().getType());
		assertEquals(USER_B_PASSWORD_ENCODED, user.getPassword());
		verify(passwordEncoder, times(1)).encode(USER_B_PASSWORD);
	}
	
	@Test(expected=ConstraintViolationException.class)
	public void createUser_byRoles_throwsForConflict(){
		when(userRepository.save(userA)).thenReturn(userA);
		when(userRepository.findOne(USER_A_NAME)).thenThrow(new ConstraintViolationException("", null, null));
		communityServiceImpl.createUser(userA, Role.ROLE_BASIC);
	}
	
	@Test
	public void updateUser_usesDAO_001() {
		when(userRepository.save(userA)).thenReturn(userA);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		when(authentication.getPrincipal()).thenReturn(userA);

		User user = communityServiceImpl.updateUser(userA);
		assertEquals(userA, user);
		
		verify(userRepository, times(1)).save(userA);
		verify(securityContext, times(1)).getAuthentication();
		verify(authentication, times(1)).getPrincipal();
	}
	
	@Test
	public void updateUser_usesDAO_002() {
		when(userRepository.save(userA)).thenReturn(userA);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		when(authentication.getPrincipal()).thenReturn(userAdmin);
		when(authentication.isAuthenticated()).thenReturn(true);

		User user = communityServiceImpl.updateUser(userA);
		assertEquals(userA, user);
		
		verify(userRepository, times(1)).save(userA);
		verify(securityContext, atLeastOnce()).getAuthentication();
		verify(authentication, atLeastOnce()).getPrincipal();
	}
	
	@Test(expected=BadCredentialsException.class)
	public void updateUser_throwsUnAuthorized() {
		when(userRepository.save(userA)).thenReturn(userA);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		when(authentication.getPrincipal()).thenReturn(userB);
		
		communityServiceImpl.updateUser(userA);
	}
	
	@Test
	public void updateUser_setAttributes() {
		when(userRepository.save(userA)).thenReturn(userA);
		when(passwordEncoder.encode(USER_A_PASSWORD)).thenReturn(USER_A_PASSWORD_ENCODED);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		when(authentication.getPrincipal()).thenReturn(userA);

		User user = communityServiceImpl.updateUser(userA);
		assertEquals(USER_A_PASSWORD_ENCODED, user.getPassword());
		verify(passwordEncoder, times(1)).encode(USER_A_PASSWORD);
	}
	
	@Test
	public void createUserWithBalance_setAttributes() {
		when(userRepository.save(userB)).thenReturn(userB);
		when(passwordEncoder.encode(USER_B_PASSWORD)).thenReturn(USER_B_PASSWORD_ENCODED);
		Role[] roles = {Role.ROLE_BASIC, Role.ROLE_OAUTH2};
		BigDecimal balance = new BigDecimal(123);
		User user = communityServiceImpl.createUserWithBalance(userB, roles, balance);
		assertEquals(balance, user.getBalance());
		assertEquals(SupportedLanguage.EN, user.getLanguage());
		assertEquals(2, user.getAuthorities().size());
		Iterator<? extends GrantedAuthority> authoritiesIterator = user.getAuthorities().iterator();
		assertEquals(Role.ROLE_BASIC.name(), authoritiesIterator.next().getAuthority());
		assertEquals(Role.ROLE_OAUTH2.name(), authoritiesIterator.next().getAuthority());
		assertEquals(1, user.getActions().size());
		assertEquals(UserActivityType.REGISTER, user.getActions().iterator().next().getType());
		assertEquals(USER_B_PASSWORD_ENCODED, user.getPassword());
		verify(passwordEncoder, times(1)).encode(USER_B_PASSWORD);
	}
	
	@Test(expected=ConstraintViolationException.class)
	public void createUserWithBalance_throwsForConflict(){
		when(userRepository.save(userA)).thenReturn(userA);
		when(userRepository.findOne(USER_A_NAME)).thenThrow(new ConstraintViolationException("", null, null));
		Role[] roles = {Role.ROLE_BASIC, Role.ROLE_OAUTH2};
		BigDecimal balance = new BigDecimal(123);
		communityServiceImpl.createUserWithBalance(userA, roles, balance);
	}
	
	@Test
	public void findOne_usesDAO() {
		when(userRepository.findOne(USER_A_NAME)).thenReturn(userA);
		User user = communityServiceImpl.findOne(USER_A_NAME);
		assertEquals(userA, user);
		verify(userRepository, times(1)).findOne(USER_A_NAME);
	}
	
	@Test
	public void delete_usesDAO() {
		communityServiceImpl.delete(USER_A_NAME);
		verify(userRepository, times(1)).delete(USER_A_NAME);
	}
	
	@Test
	public void delete_methodSecured() throws NoSuchMethodException {
		Annotation[] annotations = AnnotationUtils.getAnnotations(CommunityServiceImpl.class.getMethod("delete", String.class));
		List<Annotation> annotationsList = Arrays.asList(annotations);
		
		Optional<Annotation> securedOpt = annotationsList.stream()
													.filter(a -> a.annotationType().isAssignableFrom(Secured.class))
													.findFirst();
		
		assertTrue(securedOpt.isPresent());
		Annotation securedAnnotation = securedOpt.get();
		String[] values = (String[]) AnnotationUtils.getValue(securedAnnotation);
		assertTrue(Arrays.asList(values).contains(Role.ADMIN));
		assertTrue(Arrays.asList(values).contains(Role.SYSTEM));
	}
	
	@Test
	public void findAll_usesDAO() {
		when(userRepository.findAll(userPageable)).thenReturn(userResultsPage);
		Page<User> userPage = communityServiceImpl.findAll(userPageable);
		assertEquals(userResultsPage, userPage);
		verify(userRepository, times(1)).findAll(userPageable);
	}
	
	@Test
	public void findAll_byPageable_methodSecured() throws NoSuchMethodException {
		Annotation[] annotations = AnnotationUtils.getAnnotations(CommunityServiceImpl.class.getMethod("findAll", Pageable.class));
		List<Annotation> annotationsList = Arrays.asList(annotations);
		
		Optional<Annotation> securedOpt = annotationsList.stream()
															.filter(a -> a.annotationType().isAssignableFrom(Secured.class))
															.findFirst();
		
		assertTrue(securedOpt.isPresent());
		Annotation securedAnnotation = securedOpt.get();
		String[] values = (String[]) AnnotationUtils.getValue(securedAnnotation);
		assertTrue(Arrays.asList(values).contains(Role.ADMIN));
	}
	
	@Test
	public void search_usesDAO() {
		Specification<User> searchUserSpec = userSpecifications.idStartsWith(USER_A_NAME);
		userList = userList.stream().filter(u -> u.getId().startsWith(USER_A_NAME)).collect(Collectors.toList());
		userResultsPage = new PageImpl<>(userList, userPageable, userList.size());
		
		when(userRepository.findAll(searchUserSpec, userPageable)).thenReturn(userResultsPage);
		communityServiceImpl.search(searchUserSpec, userPageable);
		verify(userRepository, times(1)).findAll(searchUserSpec, userPageable);
	}
	
	@Test
	public void search_setFilteredAttributes() {
		Specification<User> searchUserSpec = userSpecifications.idStartsWith(USER_A_NAME);
		userList = userList.stream().filter(u -> u.getId().startsWith(USER_A_NAME)).collect(Collectors.toList());
		userResultsPage = new PageImpl<>(userList, userPageable, userList.size());
		
		when(userRepository.findAll(searchUserSpec, userPageable)).thenReturn(userResultsPage);
		Page<UserDTO> page = communityServiceImpl.search(searchUserSpec, userPageable);
		assertEquals(1, page.getContent().size());
		assertDTOWithHiddenValues(page.getContent().get(0), userA);
	}
	
	@Test
	public void getLeaders_usesDAO() {
		when(userRepository.findAll(userPageable)).thenReturn(userResultsPage);
		communityServiceImpl.getLeaders(userPageable);
		verify(userRepository, times(1)).findAll(userPageable);
	}
	
	@Test
	public void getLeaders_setFilteredAttributes() {
		when(userRepository.findAll(userPageable)).thenReturn(userResultsPage);
		Page<UserDTO> page = communityServiceImpl.getLeaders(userPageable);
		assertDTOWithHiddenValues(page.getContent().get(0), userA);
		assertDTOWithHiddenValues(page.getContent().get(1), userB);
	}
	
	@Test
	public void getUser_usesDAO() {
		when(userRepository.findOne(USER_A_NAME)).thenReturn(userA);
		communityServiceImpl.getUser(USER_A_NAME);
		verify(userRepository, times(1)).findOne(USER_A_NAME);
	}
	
	@Test(expected=ResourceNotFoundException.class)
	public void getUser_throwsResourceNotFound() {
		when(communityServiceImpl.getUser(null)).thenReturn(null);
		communityServiceImpl.getUser(null);
	}
	
	@Test
	public void getUser_setAttributes() {
		when(communityServiceImpl.findOne(USER_A_NAME)).thenReturn(userA);
		UserDTO userDTO = communityServiceImpl.getUser(USER_A_NAME);
		assertDTOWithHiddenValues(userDTO, userA);
	}
	
	@Test
	public void identifyUser_usesDAO() {
		when(userRepository.findOne(USER_A_NAME)).thenReturn(userAStored);
		when(passwordEncoder.matches(USER_A_PASSWORD, USER_A_PASSWORD_ENCODED)).thenReturn(true);
		communityServiceImpl.identifyUser(userA);
		verify(userRepository, times(1)).findOne(USER_A_NAME);
	}
	
	@Test(expected=BadCredentialsException.class)
	public void identifyUser_throwsBadCredentialsException() {
		when(userRepository.findOne(USER_A_NAME)).thenReturn(userAStored);
		when(passwordEncoder.matches(USER_A_PASSWORD, USER_A_PASSWORD_ENCODED)).thenReturn(false);
		communityServiceImpl.identifyUser(userA);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void identifyUser_throwsIllegalArgumentException_001() {
		when(userRepository.findOne(USER_A_NAME)).thenReturn(userAStored);
		when(passwordEncoder.matches(USER_A_PASSWORD, USER_A_PASSWORD_ENCODED)).thenReturn(false);
		userA.setPassword(null);
		communityServiceImpl.identifyUser(userA);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void identifyUser_throwsIllegalArgumentException_002() {
		when(userRepository.findOne(USER_A_NAME)).thenReturn(userAStored);
		when(passwordEncoder.matches(USER_A_PASSWORD, USER_A_PASSWORD_ENCODED)).thenReturn(false);
		userA.setPassword("");
		communityServiceImpl.identifyUser(userA);
	}
	
	@Test
	public void getUserByEmail_usesDAO() {
		when(userRepository.findByEmail(USER_A_EMAIL)).thenReturn(Sets.newHashSet(userAStored));
		User user = communityServiceImpl.getUserByEmail(userA.getEmail());
		verify(userRepository, times(1)).findByEmail(USER_A_EMAIL);
		assertEquals(user, userAStored);
	}
	
	@Test
	public void getUserByEmail_methodSecured() throws NoSuchMethodException {
		Annotation[] annotations = AnnotationUtils.getAnnotations(CommunityServiceImpl.class.getMethod("getUserByEmail", String.class));
		List<Annotation> annotationsList = Arrays.asList(annotations);
		
		Optional<Annotation> securedOpt = annotationsList.stream()
															.filter(a -> a.annotationType().isAssignableFrom(Secured.class))
															.findFirst();
		
		assertTrue(securedOpt.isPresent());
		Annotation securedAnnotation = securedOpt.get();
		String[] values = (String[]) AnnotationUtils.getValue(securedAnnotation);
		assertTrue(Arrays.asList(values).contains(Role.ADMIN));
	}
	
	@Test
	public void createAuthorities() {
		Role[] roles = {Role.ROLE_BASIC, Role.ROLE_OAUTH2};
		Set<Authority> authorities = communityServiceImpl.createAuthorities(roles);
		assertEquals(2, authorities.size());
		assertTrue(authorities.stream().filter(a -> a.getAuthority().equals(Role.ROLE_BASIC.name())).findAny().isPresent());
		assertTrue(authorities.stream().filter(a -> a.getAuthority().equals(Role.ROLE_OAUTH2.name())).findAny().isPresent());
	}
	
	@Test
	public void save_usesDAO() {
		when(userRepository.save(userA)).thenReturn(userAStored);
		communityServiceImpl.save(userA);
		verify(userRepository, times(1)).save(userA);
	}
	
	@Test
	public void findByLogin_usesDAO() {
		when(userRepository.findOne(USER_A_NAME)).thenReturn(userAStored);
		communityServiceImpl.findByLogin(USER_A_NAME);
		verify(userRepository, times(1)).findOne(USER_A_NAME);
	}

	@Test
	public void findByLogin_setFilteredAttributes() {
		when(userRepository.findOne(USER_A_NAME)).thenReturn(userAStored);
		UserDTO userDTO = communityServiceImpl.findByLogin(USER_A_NAME);
		assertDTOWithHiddenValues(userDTO, userA);
	}
	
	@Test
	public void registerUser_usesDAO() {
		when(userRepository.save(userA)).thenReturn(userAStored);
		communityServiceImpl.registerUser(userA);
		verify(userRepository, times(1)).save(userA);
	}
	
	@Test
	public void registerUser_reusePasswordAndEncodeIt() {
		when(passwordEncoder.encode(USER_A_PASSWORD)).thenReturn(USER_A_PASSWORD_ENCODED);
		communityServiceImpl.registerUser(userA);
		ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
		
		verify(passwordEncoder, times(1)).encode(USER_A_PASSWORD);
		verify(userRepository, times(1)).save(userArgumentCaptor.capture());
		assertEquals(userArgumentCaptor.getValue().getPassword(), USER_A_PASSWORD_ENCODED);
	}
	
	@Test
	public void registerUser_generatePasswordAndEncodeIt() {
		when(communityServiceHelper.generatePassword()).thenReturn("newlyGeneratedPassword");
		when(passwordEncoder.encode("newlyGeneratedPassword")).thenReturn("newlyGeneratedPasswordEncoded");
		ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
		
		userA.setPassword(null);
		communityServiceImpl.registerUser(userA);
		
		verify(userRepository, times(1)).save(userArgumentCaptor.capture());
		verify(passwordEncoder, times(1)).encode("newlyGeneratedPassword");
		
		String capturedGeneratedPassword = userArgumentCaptor.getValue().getPassword();
		assertEquals("newlyGeneratedPasswordEncoded", capturedGeneratedPassword);
	}
	
	@Test
	public void signInUser_authTokenSetInContext() {
		ArgumentCaptor<Authentication> authArgumentCaptor = ArgumentCaptor.forClass(Authentication.class);
		communityServiceImpl.signInUser(userA);
		verify(securityContext, times(1)).setAuthentication(authArgumentCaptor.capture());
		assertEquals(USER_A_NAME, authArgumentCaptor.getValue().getName());
		assertTrue(authArgumentCaptor.getValue().getAuthorities()
													.stream()
													.filter(a -> a.getAuthority().equals(Role.BASIC.toString()))
													.findAny()
													.isPresent());
		
		assertTrue(authArgumentCaptor.getValue().getAuthorities()
													.stream()
													.filter(a -> a.getAuthority().equals(Role.OAUTH2.toString()))
													.findAny()
													.isPresent());
	}
	
	@Test
	public void loadUserByUsername_usesDAO(){
		when(userRepository.findOne(USER_A_NAME)).thenReturn(userAStored);
		communityServiceImpl.loadUserByUsername(USER_A_NAME);
		verify(userRepository, times(1)).findOne(USER_A_NAME);
	}
	
	@Test
	public void loadUserByUsername_usesSecurityContext(){
		when(userRepository.findOne(USER_A_NAME)).thenReturn(null);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		when(authentication.getPrincipal()).thenReturn(userA);
		
		UserDetails result = communityServiceImpl.loadUserByUsername(USER_A_NAME);
		verify(securityContext, times(1)).getAuthentication();
		verify(authentication, times(1)).getPrincipal();
		assertEquals(USER_A_NAME, result.getUsername());
	}
	
	@Test(expected= ResourceAccessException.class)
	public void loadUserByUsername_throwsResourceAccessException(){
		when(userRepository.findOne(USER_A_NAME)).thenReturn(null);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		when(authentication.getPrincipal()).thenReturn(null);
		
		communityServiceImpl.loadUserByUsername(USER_A_NAME);
		verify(securityContext, times(1)).getAuthentication();
		verify(authentication, times(1)).getPrincipal();
	}
	
	@Test
	public void isAffordableToUser_can_sameCurrency(){
		boolean canAfford = communityServiceImpl.isAffordableToUser(600, stockQuote, userA, null);
		assertTrue(canAfford);
	}
	
	@Test
	public void isAffordableToUser_cant_sameCurrency(){
		boolean canAfford = communityServiceImpl.isAffordableToUser(601, stockQuote, userA, null);
		assertFalse(canAfford);
	}
	
	@Test
	public void isAffordableToUser_can_differentCurrency(){
		boolean canAfford = communityServiceImpl.isAffordableToUser(485, stockQuote2, userA, currencyExchange);
		assertTrue(canAfford);
	}
	
	@Test
	public void isAffordableToUser_cant_differentCurrency(){
		boolean canAfford = communityServiceImpl.isAffordableToUser(486, stockQuote2, userA, currencyExchange);
		assertFalse(canAfford);
	}
	
	@Test
	public void alterUserBalance_sameCurrency(){
		ArgumentCaptor<User> authArgumentCaptor = ArgumentCaptor.forClass(User.class);
		communityServiceImpl.alterUserBalance(600, stockQuote, userA, UserActivityType.BUY, null);
		verify(userRepository, times(1)).save(authArgumentCaptor.capture());
		assertEquals(0, authArgumentCaptor.getValue().getBalance().intValue());
	}
	
	@Test
	public void alterUserBalance_differentCurrency(){
		ArgumentCaptor<User> authArgumentCaptor = ArgumentCaptor.forClass(User.class);
		communityServiceImpl.alterUserBalance(485, stockQuote2, userA, UserActivityType.BUY, currencyExchange);
		verify(userRepository, times(1)).save(authArgumentCaptor.capture());
		assertEquals(28, authArgumentCaptor.getValue().getBalance().intValue());
	}
	
	private void assertDTOWithHiddenValues(UserDTO userDTO, User referenceUser){
		assertEquals(referenceUser.getId(), userDTO.getId());
		assertEquals(HIDDEN_USER_ATTRIBUTE, userDTO.getPassword());
		assertEquals(referenceUser.getBalance(), userDTO.getBalance());
		assertEquals(referenceUser.getHeadline(), userDTO.getHeadline());
		assertEquals(referenceUser.getProfileImg() != null ? referenceUser.getProfileImg() : UserDTO.NO_IMAGE, userDTO.getProfileImg());
		assertEquals(referenceUser.getCurrency(), userDTO.getCurrency());
		assertEquals(HIDDEN_USER_ATTRIBUTE, userDTO.getEmail());
		assertEquals(referenceUser.getLanguage().toString(), userDTO.getLanguage());
	}
	
	private static int countInstanceOfInList(List<?> list, Class<?> clazz){
		return (int) list.stream().filter(a -> a.getClass().isAssignableFrom(clazz)).count();
	}
	
	private static int countPublicActivitiesIn(List<?> list){
		int total = 0;
		for (Class<?> actionClass : CommunityServiceImpl.PUBLIC_ACTIVITY_TYPES){
			total = total + countInstanceOfInList(list, actionClass);
		}
		return total;
	}
}
