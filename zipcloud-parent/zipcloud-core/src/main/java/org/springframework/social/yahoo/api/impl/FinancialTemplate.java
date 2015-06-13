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

import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.social.yahoo.api.FinancialOperations;
import org.springframework.social.yahoo.api.YahooAPIType;
import org.springframework.social.yahoo.module.ChartHistoMovingAverage;
import org.springframework.social.yahoo.module.ChartHistoSize;
import org.springframework.social.yahoo.module.ChartHistoTimeSpan;
import org.springframework.social.yahoo.module.ChartType;
import org.springframework.social.yahoo.module.YahooQuote;
import org.springframework.social.yahoo.module.QuoteWrapper;
import org.springframework.web.client.RestTemplate;

import edu.zipcloud.core.converters.YahooQuoteMessageConverter;
import static org.springframework.social.yahoo.api.YahooAPIType.*;

/**
 * Base class for all Financial operations performed against the Yahoo Financial API.
 *
 * @author Alex Bretet
 */
public class FinancialTemplate extends AbstractYahooOperations implements FinancialOperations {

    private RestTemplate restTemplate;
    
    public FinancialTemplate(RestTemplate restTemplate, boolean isAuthorized, String guid) {
        super(isAuthorized, guid);
        this.restTemplate = restTemplate;
        this.restTemplate.getMessageConverters().add(new YahooQuoteMessageConverter(MediaType.APPLICATION_OCTET_STREAM));
    }

	@Override
	public List<YahooQuote> getYahooQuotes(List<String> tickers, String token)  {
        requiresAuthorization();
        
        final StringBuilder sbTickers = new StringBuilder();
        String url = "quotes.csv?s=";
        String strTickers = "";
        
        if(tickers.size() > 0){
        	tickers.forEach(t -> sbTickers.append(t).append("+"));
        	strTickers = sbTickers.toString();
        	strTickers = strTickers.substring(0, strTickers.length()-1);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+token);
        
        HttpEntity<?> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(buildUri(FINANCIAL, url.concat(strTickers).concat("&f=snopl1c1p2hgbavxc4")), HttpMethod.GET, entity , QuoteWrapper.class).getBody();
	}

	@Override
	public byte[] getYahooChart(String ticker, ChartType type,
			ChartHistoSize histoSize, ChartHistoMovingAverage histoAverage,
			ChartHistoTimeSpan histoPeriod, Integer intradayWidth,
			Integer intradayHeight, String token) {
		
	        requiresAuthorization();
	        final StringBuilder sb = new StringBuilder("?s="+ticker);
	        YahooAPIType apiType = null;
	        if(type.equals(ChartType.HISTO)){
	        	if(histoSize!=null){
	        		sb.append("&z="+histoSize.getTag());
	        	}
	        	if(histoAverage!=null){
	        		sb.append("&p="+histoAverage.getTag());
	        	}
	        	if(histoPeriod!=null){
	        		sb.append("&t="+histoPeriod.getTag());
	        	}
	        	apiType = YahooAPIType.FINANCIAL_CHARTS_HISTO;
	        }
	        else{
	        	if(intradayWidth!=null){
	        		sb.append("&width="+intradayWidth);
	        	}
	        	if(intradayHeight!=null){
	        		sb.append("&height="+intradayHeight);
	        	}
	        	apiType = YahooAPIType.FINANCIAL_CHARTS_INTRA;
	        }

	        HttpHeaders headers = new HttpHeaders();
	        headers.set("Authorization", "Bearer "+token);
	        return restTemplate.exchange(buildUri(apiType, sb.toString()), HttpMethod.GET, new HttpEntity<>(headers) , byte[].class).getBody();
	}
}