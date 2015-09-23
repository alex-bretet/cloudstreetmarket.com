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

import java.io.Serializable;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.support.OAuth2Connection;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2ServiceProvider;
import org.springframework.social.yahoo.api.Yahoo2;
import org.springframework.social.yahoo.module.YahooAccessGrant;

/**
* Yahoo ConnectionFactory implementation.
* @author Alex Bretet
* @author Michael Lavelle
*/
public class YahooOAuth2ConnectionFactory extends OAuth2ConnectionFactory<Yahoo2> implements Serializable {

		private static final long serialVersionUID = -4835304974503363071L;

		public YahooOAuth2ConnectionFactory(String clientId, String clientSecret) {
			super("yahoo", new YahooOAuth2ServiceProvider(clientId, clientSecret), new YahooOAuth2Adapter());
		}
		public YahooOAuth2ConnectionFactory(String clientId, String clientSecret, String redirectUri) {
			super("yahoo", new YahooOAuth2ServiceProvider(clientId, clientSecret, redirectUri), new YahooOAuth2Adapter());
		}
		
		public Connection<Yahoo2> createConnection(AccessGrant accessGrant) {
			return new OAuth2Connection<Yahoo2>(getProviderId(), extractProviderUserId(accessGrant), accessGrant.getAccessToken(),
					accessGrant.getRefreshToken(), accessGrant.getExpireTime(), getOAuth2ServiceProviderWithGuid(accessGrant), getApiAdapter());		
		}
		
		private OAuth2ServiceProvider<Yahoo2> getOAuth2ServiceProviderWithGuid(AccessGrant accessGrant) {
			YahooOAuth2ServiceProvider yahooSP = (YahooOAuth2ServiceProvider) getServiceProvider();
			yahooSP.setGuid(((YahooAccessGrant) accessGrant).getXoauthYahooGuid());
			return yahooSP;
		}
		
		/**
		 * Hook for extracting the providerUserId from the returned {@link AccessGrant}, if it is available.
		 * Default implementation returns null, indicating it is not exposed and another remote API call will be required to obtain it.
		 * Subclasses may override.
		 */
		protected String extractProviderUserId(AccessGrant accessGrant) {
			return ((YahooAccessGrant) accessGrant).getXoauthYahooGuid();
		}
}
