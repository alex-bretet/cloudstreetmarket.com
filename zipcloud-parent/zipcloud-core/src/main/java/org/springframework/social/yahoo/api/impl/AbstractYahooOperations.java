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

import java.net.URI;
import java.util.Arrays;

import org.springframework.social.NotAuthorizedException;
import org.springframework.social.support.URIBuilder;
import org.springframework.social.yahoo.api.YahooAPIType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * Base class for all operations performed against the Yahoo Social API.
 *
 * @author Ruiu Gabriel Mihai (gabriel.ruiu@mail.com)
 */
abstract class AbstractYahooOperations {

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

    protected String getApiUrlBase(YahooAPIType apiType) {
    	return apiType.getBaseUrl();
    }

    protected URI buildUri(YahooAPIType api, String path) {
        return buildUri(api, path, EMPTY_PARAMETERS);
    }

    protected URI buildUri(YahooAPIType api, String path, String parameterName, String parameterValue) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
        parameters.set(parameterName, parameterValue);
        return buildUri(api, path, parameters);
    }

    protected URI buildUri(YahooAPIType api, String path, MultiValueMap<String, String> parameters) {
    	if(api.equals(YahooAPIType.FINANCIAL) || api.equals(YahooAPIType.FINANCIAL_CHARTS_HISTO) || api.equals(YahooAPIType.FINANCIAL_CHARTS_INTRA)){
    		try {
				return new URI(getApiUrlBase(api) + path.replaceAll("\\^", "%5E"));
			} catch (Exception e) {
				throw new IllegalArgumentException("The following url failed to build! "+ getApiUrlBase(api) + path.replaceAll("\\^", "%5E"));
			}
    	}
    	else{
    		parameters.put("format", Arrays.asList("json"));
    		return URIBuilder.fromUri(String.format(getApiUrlBase(api) + path, guid)).queryParams(parameters).build();
    	}
    }

}