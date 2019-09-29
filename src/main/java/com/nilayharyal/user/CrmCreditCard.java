package com.nilayharyal.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CrmCreditCard {

@NotNull(message = "is required")
@Size(min = 1, message = "is required")
String cardName;

@NotNull(message = "is required")
@Size(min = 1, message = "is required")
String cardNumber;

int expMonth;
	
int expYear;

public String getCardName() {
	return cardName;
}

public void setCardName(String cardName) {
	this.cardName = cardName;
}

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
