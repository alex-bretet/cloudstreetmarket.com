package edu.zipcloud.cloudstreetmarket.portal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.zipcloud.cloudstreetmarket.core.services.ICommunityService;
import edu.zipcloud.cloudstreetmarket.core.services.IMarketService;

@Controller
public class DefaultController extends CloudstreetWebAppWCI {
	
	@Autowired
	@Qualifier("marketServiceImpl")
	private IMarketService marketService;
	
	@Autowired
	@Qualifier("communityServiceImpl")
	private ICommunityService communityService;
		
	@RequestMapping(value="/*", method={RequestMethod.GET,RequestMethod.HEAD})
	public String fallback(Model model) {
		model.addAttribute("dailyMarketActivity",  marketService.getLastDayIndexActivity("GDAXI"));
		model.addAttribute("dailyIndicesActivity", marketService.getLastDayIndexOverview("europe"));
		model.addAttribute("recentUserActivity", communityService.getLastUserPublicActivity(10));
		return "index";
	}
}
