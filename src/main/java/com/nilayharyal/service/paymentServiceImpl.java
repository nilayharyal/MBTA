package com.nilayharyal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nilayharyal.dao.PaymentDao;
import com.nilayharyal.entity.CreditCard;

@Service
public class paymentServiceImpl implements PaymentService {

	@Autowired
	PaymentDao paymentDao;
	
	@Override
	@Transactional
	public CreditCard findbyCardNumber(String CardNumber) {
		return paymentDao.findBycardNumber(CardNumber);
	}

}
