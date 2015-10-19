/***
 *  Cloudstreetmarket.com is a Spring MVC showcase application developed 
 *  with the book Spring MVC Cookbook [PACKT] (2015). 
 * 	Copyright (C) 2015  Alex Bretet
 *  
 *  This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 **/
package edu.zipcloud.cloudstreetmarket.ws.controllers;

import static edu.zipcloud.cloudstreetmarket.shared.util.Constants.*;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import edu.zipcloud.cloudstreetmarket.core.entities.StockProduct;
import edu.zipcloud.cloudstreetmarket.shared.services.StockProductServiceOffline;

@RestController
public class StockProductWSController extends CloudstreetWebSocketWCI<StockProduct>{

	@Autowired
	private StockProductServiceOffline stockProductService;

    @MessageMapping("/queue/CSM_QUEUE_{queueId}")
    @SendTo("/queue/CSM_QUEUE_{queueId}")
	@PreAuthorize("hasRole('OAUTH2')")
    public List<StockProduct> sendContent(@Payload List<String> tickers, @DestinationVariable("queueId") String queueId) throws Exception {
    	String username = extractUserFromQueueId(queueId);
    	if(!getPrincipal().getUsername().equals(username)){
    		throw new IllegalAccessError("/queue/CSM_QUEUE_"+queueId);
    	}
		return stockProductService.gather(username, tickers.toArray(new String[tickers.size()]));
    }
    
    @RequestMapping(value=PRIVATE_STOCKS_ENDPOINT+"/info", produces={"application/xml", "application/json"})
	@ResponseBody
	@PreAuthorize("hasRole('OAUTH2')")
	public String info(HttpServletRequest request) {
	    return "v0";
	}
    
    private static String extractUserFromQueueId(String token){
    	Pattern p = Pattern.compile("_[0-9]+$");
    	Matcher m = p.matcher(token);
    	String sessionNumber = m.find() ? m.group() : "";
    	return token.replaceAll(sessionNumber, "");
    }
}