package com.nilayharyal.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@Entity
@Table(name="Bank_Account")
@PrimaryKeyJoinColumn(name="owner_id")
public class BankAccount extends BillingDetails{

@Column(name="accountNo")
String accountNo;

String cnfAccountNo;

@Column(name="bankName")
String bankName;

@Column(name="routingNo")
String routingNo;

@Column(name="accountType")
String accountType;

public String getAccountType() {
	return accountType;
}
public void setAccountType(String accountType) {
	this.accountType = accountType;
}
public String getAccountNo() {
	return accountNo;
}
public void setAccountNo(String accountNo) {
	this.accountNo = accountNo;
}
public String getBankName() {
	return bankName;
}
public void setBankName(String bankName) {
	this.bankName = bankName;
}
public String getRoutingNo() {
	return routingNo;
}
public void setRoutingNo(String routingNo) {
	this.routingNo = routingNo;
}


}
