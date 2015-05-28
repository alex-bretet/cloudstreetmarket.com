package edu.zipcloud.cloudstreetmarket.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wordnik.swagger.annotations.Api;

@Api(value = "products", description = "Financial products") // Swagger annotation
@RestController
@RequestMapping(AbstractProductController.PRODUCT_PATH)
public abstract class AbstractProductController extends CloudstreetApiWCI{
	
	public static final String PRODUCT_PATH = "/products";
	
}