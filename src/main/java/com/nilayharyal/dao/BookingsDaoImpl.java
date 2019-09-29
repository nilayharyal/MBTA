package com.nilayharyal.dao;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.nilayharyal.entity.Bookings;
import com.nilayharyal.entity.User;
import com.nilayharyal.exception.UserException;

@Repository
public class BookingsDaoImpl implements BookingsDao{

	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public Long availability(String trainName,String travelDate){
		Session currentSession = sessionFactory.getCurrentSession();
		Long availableSeats = (long) 0;
		try {
		Query cr = currentSession.createQuery("select sum(tickets) from Bookings where trainName=:trainName and travelDate=:travelDate");
		cr.setString("trainName",trainName);
		cr.setString("travelDate",travelDate);
		cr.setMaxResults(1);
		if(cr.uniqueResult() == null) {
		availableSeats = (long) 50;
		}else {
		availableSeats = 50 - (Long)cr.uniqueResult();
		}
		}catch(Exception e) {
			System.out.println("Exception encountered while fetching seats available: "+e);
		}
		return availableSeats;
	}
	
	@Override
	@Transactional
	public void book(Bookings b) throws UserException {
		try {
			Session currentSession = sessionFactory.getCurrentSession();
			currentSession.save(b);
		} catch (HibernateException e) {
			throw new UserException("Exception while making booking: " + e.getMessage());
		}
	}

	@Transactional
	public void delete(Long id) {
		try {
			Session currentSession = sessionFactory.getCurrentSession();
			Query query = currentSession.createQuery("from Bookings b where b.bookingsId=:bookingsId");
			query.setLong("bookingsId", id);
			Bookings b=(Bookings)query.uniqueResult();
			Bookings toDelete = (Bookings) currentSession.merge(b);
			currentSession.delete(toDelete);
		}catch(Exception e) {
			System.out.println(e);
		}
	}

	@Transactional
	public List<Bookings> mybookings(User user) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query query = 	currentSession.createQuery("from Bookings where user=:id");
		query.setLong("id", user.getId());
		List<Bookings> result = query.list();
		return result;
	}
	
	
}
