package com.nilayharyal.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nilayharyal.entity.BankAccount;
import com.nilayharyal.entity.Bookings;
import com.nilayharyal.entity.CreditCard;
import com.nilayharyal.entity.User;

@Repository
public class PaymentDaoImpl implements PaymentDao {

@Autowired
private SessionFactory sessionFactory;

@Override
@Transactional
public Bookings createBooking(Bookings b) {
	try {
		Session currentSession = sessionFactory.getCurrentSession();
		
		currentSession.save(b);
		} catch (HibernateException e) {
		System.out.println(e.getMessage());
	}
	return b;
}

@Transactional
public CreditCard create(CreditCard creditCard) {
	Session currentSession = sessionFactory.getCurrentSession();
	try {
	currentSession.save(creditCard);
	}catch(Exception e) {
		System.out.println("error:"+e);
	}
	return creditCard;
}

@Transactional
public BankAccount create(BankAccount bankAccount) {
	Session currentSession = sessionFactory.getCurrentSession();
	try {
	currentSession.save(bankAccount);
	}catch(Exception e) {
		System.out.println("error:"+e);
	}
	return bankAccount;
}


@Override
@Transactional
public void deleteCard(CreditCard creditCard) {
	Session currentSession = sessionFactory.getCurrentSession();
	Query query = currentSession.createQuery("delete from CreditCard where id=:owner_id");
	query.setLong("owner_id",retrieveCard(creditCard.getOwner()).getId());
	int result = query.executeUpdate();
}


@Override
@Transactional
public void deleteAccount(BankAccount bankAccount) {
	Session currentSession = sessionFactory.getCurrentSession();
	Query query = currentSession.createQuery("delete from BankAccount where id=:owner_id");
	query.setLong("owner_id",retrieveAccount(bankAccount.getOwner()).getId());
	int result = query.executeUpdate();
Bookings bookings = new Bookings();
}


@Transactional
public CreditCard retrieveCard(User user) {
	Session currentSession = sessionFactory.getCurrentSession();
	Criteria cr = currentSession.createCriteria(CreditCard.class);
	List<CreditCard> results = cr.list();
	try {
	for(CreditCard c: results) {
		if(c.getOwner().getId()==user.getId()) {
			return c;
		}
	}}catch(Exception e) {
		System.out.println(e);
	}
	return null;
}

@Transactional
public CreditCard update(CreditCard creditCard) {
	Session currentSession = sessionFactory.getCurrentSession();
//	Long in = retrieveCard(creditCard.getOwner()).getId();
	String hql="update CreditCard set cardName=:cardName, cardNumber=:cardNumber,expMonth=:expMonth,expYear=:expYear where id=:id";
	Query query = currentSession.createQuery(hql);
	query.setString("cardName", creditCard.getCardName());
	query.setString("cardNumber", creditCard.getCardNumber());
	query.setInteger("expMonth", creditCard.getExpMonth());
	query.setInteger("expYear", creditCard.getExpYear());
	query.setLong("id",creditCard.getId());
	int result = query.executeUpdate();
	return creditCard;
}

@Transactional
public BankAccount update(BankAccount bankAccount) {
	Session currentSession = sessionFactory.getCurrentSession();
//	Long in = retrieveAccount(bankAccount.getOwner()).getId();
	String hql="update BankAccount set accountNo=:accountNo, bankName=:bankName,routingNo=:routingNo,accountType=:accountType where id=:owner_id";
	Query query = currentSession.createQuery(hql);
	query.setString("accountNo", bankAccount.getAccountNo());
	query.setString("bankName", bankAccount.getBankName());
	query.setString("routingNo", bankAccount.getRoutingNo());
	query.setString("accountType", bankAccount.getAccountType());
	query.setLong("owner_id", bankAccount.getId());
	int result = query.executeUpdate();
	return bankAccount;
}


@Transactional
public BankAccount retrieveAccount(User user) {
	Session currentSession = sessionFactory.getCurrentSession();
	Criteria cr = currentSession.createCriteria(BankAccount.class);
	List<BankAccount> results = cr.list();
	try {
	for(BankAccount ba: results) {
		if(ba.getOwner().getId()==user.getId()) {
			return ba;
		}
	}}catch(Exception e) {
		System.out.println(e);
	}
	return null;
}

@Transactional
public List<CreditCard> retrieveCards(User user) {
	List<CreditCard>cards=new <CreditCard>ArrayList();
	Session currentSession = sessionFactory.getCurrentSession();
	Query query= currentSession.createQuery("from CreditCard");
	List<CreditCard>results=query.list();
	try {
	for(CreditCard c: results) {
		if(c.getOwner().getId()==user.getId()) {
			cards.add(c);
		}
	}}catch(Exception e) {
		System.out.println(e);
	}
	return cards;
}

@Transactional
public List<BankAccount> retrieveAccounts(User user) {
	List<BankAccount>accounts=new <BankAccount>ArrayList();
	Session currentSession = sessionFactory.getCurrentSession();
	Query query= currentSession.createQuery("from BankAccount");
	List<BankAccount> results=query.list();
	try {
	for(BankAccount c: results) {
		if(c.getOwner().getId()==user.getId()) {
			accounts.add(c);
		}
	}}catch(Exception e) {
		System.out.println(e);
	}
	return accounts;
}

@Transactional
public CreditCard findBycardNumber(String cardNumber) {
	Session currentSession = sessionFactory.getCurrentSession();

	
	Query<CreditCard> theQuery = currentSession.createQuery("from CreditCard where cardNumber=:cardNumber", CreditCard.class);
	theQuery.setParameter("cardNumber", cardNumber);
	CreditCard creditCard = null;
	try {
		theQuery.setMaxResults(1);
		creditCard = theQuery.getSingleResult();
	} catch (Exception e) {
		creditCard = null;
	}

	return creditCard;
} 

@Transactional
public BankAccount findByAccountNumber(String accountNumber) {
	Session currentSession = sessionFactory.getCurrentSession();

	
	Query<BankAccount> theQuery = currentSession.createQuery("from BankAccount where accountNo=:accountNo", BankAccount.class);
	theQuery.setParameter("accountNo", accountNumber);
	BankAccount bankAccount = null;
	try {
		theQuery.setMaxResults(1);
		bankAccount = theQuery.getSingleResult();
	} catch (Exception e) {
		bankAccount = null;
	}

	return bankAccount;
}   
}
