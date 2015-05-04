/**
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.yahoo.api.impl;

import org.springframework.social.yahoo.api.ProfilesOperations;
import org.springframework.social.yahoo.module.TinyUsercard;
import org.springframework.web.client.RestTemplate;

/**
 * Base class for all Profile operations performed against the Yahoo Social API.
 *
 * @author Alex Bretet
 */
public class ProfileTemplate extends AbstractYahooOperations implements ProfilesOperations {

    private RestTemplate restTemplate;

    public ProfileTemplate(RestTemplate restTemplate, boolean isAuthorized, String guid) {
        super(isAuthorized, guid);
        this.restTemplate = restTemplate;
    }

	@Override
	public TinyUsercard getTinyUsercard() {
        requiresAuthorization();
        return restTemplate.getForObject(buildUri("/profile/tinyusercard"), TinyUsercard.class);
	}
}