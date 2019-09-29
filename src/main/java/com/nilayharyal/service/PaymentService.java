package com.nilayharyal.service;

import com.nilayharyal.entity.CreditCard;


public interface PaymentService {

	CreditCard findbyCardNumber( String getCardNumber);
}
