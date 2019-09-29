package com.nilayharyal.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.nilayharyal.entity.CreditCard;
import com.nilayharyal.service.PaymentService;




public class BookingsValidator implements Validator{

	@Autowired
	private PaymentService paymentService;

	
	@Override
	public boolean supports(Class<?> cardClass) {
		return CreditCard.class.isAssignableFrom(cardClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		CreditCard creditCard = (CreditCard)target;
		if (paymentService.findbyCardNumber(creditCard.getCardNumber())!=null) {
			errors.rejectValue("cardNumber","duplicate.creditCard.cardNumber");
		}
	}

}
