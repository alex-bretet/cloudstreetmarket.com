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

import org.springframework.social.ApiException;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.YahooUserProfile;
import org.springframework.social.yahoo.api.Yahoo2;
import org.springframework.social.yahoo.module.TinyUsercard;

/**
* Yahoo OAuth2 ApiAdapter implementation.
* @author Alex Bretet
*/
public class YahooOAuth2Adapter implements ApiAdapter<Yahoo2> {

	@Override
	public UserProfile fetchUserProfile(Yahoo2 yahoo) {
		TinyUsercard tinyUserCard = yahoo.profilesOperations().getTinyUsercard();
		return new YahooUserProfile(
				tinyUserCard.getProfile().getNickname(), 
				tinyUserCard.getProfile().getGivenName(), 
				tinyUserCard.getProfile().getFamilyName(), 
				null, 
				tinyUserCard.getProfile().getGuid());
	}

	@Override
	public void setConnectionValues(Yahoo2 yahoo, ConnectionValues values) {

	}

	@Override
	public boolean test(Yahoo2 yahoo) {
		try {
			return true;
		} catch (ApiException e) {
			return false;
		}
	}

	@Override
	public void updateStatus(Yahoo2 yahoo, String arg1) {}
}