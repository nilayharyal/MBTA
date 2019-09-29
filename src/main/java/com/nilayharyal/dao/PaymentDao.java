package com.nilayharyal.dao;

import java.util.List;

import com.nilayharyal.entity.BankAccount;
import com.nilayharyal.entity.Bookings;
import com.nilayharyal.entity.CreditCard;
import com.nilayharyal.entity.User;

public interface PaymentDao {
	
	public Bookings createBooking(Bookings b);
	
	public void deleteCard(CreditCard creditCard);
	public void deleteAccount(BankAccount bankAccount);
	
	public CreditCard create(CreditCard creditCard);
	public BankAccount create(BankAccount bankAccount);
	
	public CreditCard update(CreditCard creditCard);
	public BankAccount update(BankAccount bankAccount);
	
	public CreditCard retrieveCard(User user);
	public BankAccount retrieveAccount(User user);
	
	public List<CreditCard> retrieveCards(User user);
	public List<BankAccount> retrieveAccounts(User user);
	
	public CreditCard findBycardNumber(String cardNumber);
	public BankAccount findByAccountNumber(String accountNumber);

}
