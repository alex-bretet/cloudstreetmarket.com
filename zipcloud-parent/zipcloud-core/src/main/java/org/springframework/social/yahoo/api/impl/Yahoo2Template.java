/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.yahoo.api.impl;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.OAuth2Version;
import org.springframework.social.support.ClientHttpRequestFactorySelector;
import org.springframework.social.yahoo.api.ContactsOperations;
import org.springframework.social.yahoo.api.FinancialOperations;
import org.springframework.social.yahoo.api.ProfilesOperations;
import org.springframework.social.yahoo.api.Yahoo2;
import org.springframework.social.yahoo.module.YahooModule;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
* @author Alex Bretet
* @author Michael Lavelle
*/
public class Yahoo2Template extends AbstractOAuth2ApiBinding implements Yahoo2{
	
	 private ProfilesOperations profilesOperations;
	 private ContactsOperations contactsOperations;
	 private FinancialOperations financialOperations;

	@Override
	protected OAuth2Version getOAuth2Version() {
		return OAuth2Version.BEARER;
	}
	
	/**
	 * Create a new instance of Yahoo2Template. This constructor creates a
	 * new Yahoo2Template able to perform unauthenticated operations against
	 * Yahoo!'s API. 
	 * Operations requiring authentication will throw {@link NotAuthorizedException}.
	 */
	public Yahoo2Template(String clientId) {
		initialize(clientId, null);
	}

	/**
	 * Create a new instance of Yahoo2Template. This constructor creates the
	 * Yahoo2Template using a given access token.
	 * 
	 * @param accessToken
	 *            An access token given by Yahoo! after a successful OAuth 2
	 *            authentication
	 */
	public Yahoo2Template(String clientId, String accessToken) {
		super(accessToken);
		initialize(clientId, accessToken);
	}
	
	/**
	 * Create a new instance of Yahoo2Template. This constructor creates the
	 * Yahoo2Template using a given access token.
	 * 
	 * @param accessToken
	 *            An access token given by Yahoo! after a successful OAuth 2
	 *            authentication
	 */
	public Yahoo2Template(String clientId, String accessToken, String guid) {
		super(accessToken);
		initialize(clientId, accessToken, guid);
	}
	
	private void initialize(String clientId, String accessToken) {
		initialize(clientId, accessToken, null);
	}
	
	// private helpers
	private void initialize(String clientId, String accessToken, String guid) {
		// Wrap the request factory with a BufferingClientHttpRequestFactory so
		// that the error handler can do repeat reads on the response.getBody()
		super.setRequestFactory(ClientHttpRequestFactorySelector
				.bufferRequests(getRestTemplate().getRequestFactory()));
		initSubApis(clientId, accessToken, guid);
	}
	
	@Override
    public ContactsOperations contactsOperations() {
        return contactsOperations;
    }

	@Override
	public ProfilesOperations profilesOperations() {
		return profilesOperations;
	}
    
    @Override
    protected MappingJackson2HttpMessageConverter getJsonMessageConverter() {
        MappingJackson2HttpMessageConverter converter = super.getJsonMessageConverter();
        converter.setObjectMapper(createObjectMapper());
        return converter;
    }

    @Override
    protected void configureRestTemplate(RestTemplate restTemplate) {
        restTemplate.setErrorHandler(errorHandler());
    }

    protected ResponseErrorHandler errorHandler() {
        return new DefaultResponseErrorHandler();
    }

    protected void initSubApis(String clientId, String accessToken, String guid) {
        this.contactsOperations = new ContactsTemplate(getRestTemplate(), isAuthorized(), guid);
        this.profilesOperations = new ProfileTemplate(getRestTemplate(), isAuthorized(), guid);
        this.financialOperations = new FinancialTemplate(getRestTemplate(), isAuthorized(), guid);
    }

    protected ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new YahooModule());
        return mapper;
    }

	@Override
	public FinancialOperations financialOperations() {
		return financialOperations;
	}
}
