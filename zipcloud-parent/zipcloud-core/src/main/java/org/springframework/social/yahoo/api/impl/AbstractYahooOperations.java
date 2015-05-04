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

import org.springframework.social.NotAuthorizedException;
import org.springframework.social.support.URIBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URI;
import java.util.Arrays;

/**
 * Base class for all operations performed against the Yahoo Social API.
 *
 * @author Ruiu Gabriel Mihai (gabriel.ruiu@mail.com)
 */
abstract class AbstractYahooOperations {

    private static final String API_URL_BASE = "https://social.yahooapis.com/v1/user/%s";
    private static final LinkedMultiValueMap<String, String> EMPTY_PARAMETERS = new LinkedMultiValueMap<String, String>();

    private boolean isAuthorized;
    private String guid;

    public AbstractYahooOperations(boolean isAuthorized, String guid) {
        this.isAuthorized = isAuthorized;
        this.guid = guid;
    }

    protected void requiresAuthorization() {
        if (!isAuthorized) {
            throw new NotAuthorizedException("yahoo", "app has not been authorized");
        }
    }

    protected String getApiUrlBase() {
        return API_URL_BASE;
    }

    protected URI buildUri(String path) {
        return buildUri(path, EMPTY_PARAMETERS);
    }

    protected URI buildUri(String path, String parameterName, String parameterValue) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
        parameters.set(parameterName, parameterValue);
        return buildUri(path, parameters);
    }

    protected URI buildUri(String path, MultiValueMap<String, String> parameters) {
        parameters.put("format", Arrays.asList("json"));
        return URIBuilder.fromUri(String.format(getApiUrlBase() + path, guid)).queryParams(parameters).build();
    }
}