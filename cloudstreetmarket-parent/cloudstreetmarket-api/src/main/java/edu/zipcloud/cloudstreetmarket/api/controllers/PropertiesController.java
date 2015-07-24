package edu.zipcloud.cloudstreetmarket.api.controllers;

import java.util.Locale;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wordnik.swagger.annotations.Api;

import edu.zipcloud.cloudstreetmarket.core.entities.Transaction;
import edu.zipcloud.cloudstreetmarket.core.i18n.SerializableResourceBundleMessageSource;

@Api(value = "Properties", description = "Properties") // Swagger annotation
@RestController
@ExposesResourceFor(Transaction.class)
@RequestMapping(value="/properties")
public class PropertiesController{
	
	@Autowired
	protected SerializableResourceBundleMessageSource messageBundle;
	
	@RequestMapping(method = RequestMethod.GET, produces={"application/json; charset=UTF-8"})
	@ResponseBody
	public Properties list(@RequestParam String lang) {
	    return messageBundle.getAllProperties(new Locale(lang));
	}
}
