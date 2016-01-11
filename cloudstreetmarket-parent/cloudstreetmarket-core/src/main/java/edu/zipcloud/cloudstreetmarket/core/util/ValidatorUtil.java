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
package edu.zipcloud.cloudstreetmarket.core.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;

public class ValidatorUtil {

	  private static Validator validator;
	  
	  static {
	    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	    validator = factory.getValidator();
	  }
	  
	  public static <T> Map<String, String> validate(T object, Class<?>... groups) {
	    Class<?>[] args = Arrays.copyOf(groups, groups.length + 1);
	    args[groups.length] = Default.class;
	    return extractViolations(validator.validate(object, args));
	  }
	  
	  private static <T> Map<String, String> extractViolations(Set<ConstraintViolation<T>> violations) {
	    Map<String, String> errors = new HashMap<String, String>();
	    for (ConstraintViolation<T> v: violations) {
	      errors.put(v.getPropertyPath().toString(), "["+v.getPropertyPath().toString()+"] " + StringUtils.capitalize(v.getMessage()));
	    }
	    return errors;
	  }
	  
	  public static void raiseFirstError(BindingResult result) {
		  	if (result.hasErrors()) {
	            throw new IllegalArgumentException(result.getAllErrors().get(0).getCode());
	        }
		  	else if (result.hasGlobalErrors()) {
	            throw new IllegalArgumentException(result.getGlobalError().getDefaultMessage());
		  	}
	  }
}