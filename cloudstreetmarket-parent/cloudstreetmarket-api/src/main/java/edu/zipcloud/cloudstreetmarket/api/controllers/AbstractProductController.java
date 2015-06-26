package edu.zipcloud.cloudstreetmarket.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wordnik.swagger.annotations.Api;

import edu.zipcloud.cloudstreetmarket.core.entities.Product;

@Api(value = "products", description = "Financial products") // Swagger annotation
@RestController
@RequestMapping(AbstractProductController.PRODUCT_PATH)
public abstract class AbstractProductController<T extends Product> extends CloudstreetApiWCI<T>{
	
	public static final String PRODUCT_PATH = "/products";
	
}