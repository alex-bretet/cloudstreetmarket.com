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
package org.springframework.social.yahoo.connect;

import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.yahoo.api.Yahoo2;
import org.springframework.social.yahoo.api.impl.Yahoo2Template;

/**
* Yahoo ServiceProvider.
* @author Alex Bretet
*/
public class YahooOAuth2ServiceProvider extends AbstractOAuth2ServiceProvider<Yahoo2>{

	private String clientId;
	private String guid;
	
	public YahooOAuth2ServiceProvider(String clientId, String clientSecret) {
		this(clientId, clientSecret, null);
	}

	public YahooOAuth2ServiceProvider(String clientId, String clientSecret, String redirectUri) {
		super(new YahooOAuth2Template(clientId, clientSecret, redirectUri));
		this.clientId = clientId;
	}

	@Override
	public Yahoo2 getApi(String accessToken) {
		return new Yahoo2Template(clientId, accessToken, guid);
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}
}