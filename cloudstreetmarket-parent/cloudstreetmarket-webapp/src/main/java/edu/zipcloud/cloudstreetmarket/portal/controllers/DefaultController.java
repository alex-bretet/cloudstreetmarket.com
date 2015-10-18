package edu.zipcloud.cloudstreetmarket.portal.controllers;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.zipcloud.cloudstreetmarket.core.services.CommunityService;
import edu.zipcloud.cloudstreetmarket.core.services.MarketService;

@Controller
public class DefaultController extends CloudstreetWebAppWCI {
	
	@Autowired
	@Qualifier("marketServiceImpl")
	private MarketService marketService;
	
	@Autowired
	private CommunityService communityService;
		
	@RequestMapping(value="/*", method={RequestMethod.GET,RequestMethod.HEAD})
	public String fallback(Model model, @RequestParam(value="spi", required=false) String spi) {
		
		if(!StringUtils.isBlank(spi)){
			model.addAttribute("spi", spi);
		}

		return "index";
	}
}