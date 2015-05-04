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
package org.springframework.social.yahoo.api;

import org.springframework.social.yahoo.module.TinyUsercard;

/**
 * Main interface for interacting with the Profile-part of the Yahoo Social API.
 *
 * @see <a href="https://developer.yahoo.com/social/rest_api_guide/extended-profile-resource.html">Profile API</a>
 *
 * @author Alex Bretet
 */
public interface ProfilesOperations {

    /**
     * Retrieves the tinyUserCard of the logged in user
     */
	TinyUsercard getTinyUsercard();

}