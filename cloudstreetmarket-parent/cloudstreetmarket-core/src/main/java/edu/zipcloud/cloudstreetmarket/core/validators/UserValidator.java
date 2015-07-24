package edu.zipcloud.cloudstreetmarket.core.validators;

import java.util.Map;

import javax.validation.groups.Default;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import edu.zipcloud.cloudstreetmarket.core.entities.User;
import edu.zipcloud.cloudstreetmarket.core.util.ValidatorUtil;

public class UserValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors err) {
		Map<String, String> fieldValidation = ValidatorUtil.validate((User)target, Default.class);
		
		fieldValidation.forEach(
				(k, v) -> err.rejectValue(k, v)
		);
	}
}
