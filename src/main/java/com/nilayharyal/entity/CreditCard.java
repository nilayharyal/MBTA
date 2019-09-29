package com.nilayharyal.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@Entity
@Table(name="Credit_Card")
@PrimaryKeyJoinColumn(name="owner_id")
public class CreditCard extends BillingDetails{
	
	public CreditCard(){}
	
	@Column(name="cardName")
	String cardName;
	
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	@Column(name="cardNumber")
	String cardNumber;
	
	@Column(name="expMonth")
	int expMonth;
	
	@Column(name="expYear")
	int expYear;
	
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public int getExpMonth() {
		return expMonth;
	}
	public void setExpMonth(int expMonth) {
		this.expMonth = expMonth;
	}
	public int getExpYear() {
		return expYear;
	}
	public void setExpYear(int expYear) {
		this.expYear = expYear;
	}
	
	
}
