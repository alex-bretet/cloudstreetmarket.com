package edu.zipcloud.cloudstreetmarket.ws.controllers;

import static edu.zipcloud.cloudstreetmarket.shared.util.Constants.*;

import java.util.List;

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

    @MessageMapping("/queue/CSM_DEV_{username}")
    @SendTo("/queue/CSM_DEV_{username}")
	@PreAuthorize("hasRole('OAUTH2')")
    public List<StockProduct> sendContent(@Payload List<String> tickers, @DestinationVariable("username") String username) throws Exception {
		return stockProductService.gather(username, tickers.toArray(new String[tickers.size()]));
    }
    
    @RequestMapping(value=PRIVATE_STOCKS_ENDPOINT+"/info", produces={"application/xml", "application/json"})
	@ResponseBody
	@PreAuthorize("hasRole('OAUTH2')")
	public String info(HttpServletRequest request) {
	    return "v0";
	}
}