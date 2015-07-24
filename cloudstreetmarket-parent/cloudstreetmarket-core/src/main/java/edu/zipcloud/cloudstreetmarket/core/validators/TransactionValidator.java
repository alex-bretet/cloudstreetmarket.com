package edu.zipcloud.cloudstreetmarket.core.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import edu.zipcloud.cloudstreetmarket.core.entities.Transaction;

public class TransactionValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Transaction.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

	}
}
