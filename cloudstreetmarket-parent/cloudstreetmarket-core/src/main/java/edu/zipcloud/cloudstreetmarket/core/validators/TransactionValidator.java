package edu.zipcloud.cloudstreetmarket.core.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.zipcloud.cloudstreetmarket.core.entities.Transaction;

@Component
public class TransactionValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Transaction.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "quote", "transaction.quote.empty");
		ValidationUtils.rejectIfEmpty(errors, "user", "transaction.user.empty");
		ValidationUtils.rejectIfEmpty(errors, "type", "transaction.type.empty");
	}
}
