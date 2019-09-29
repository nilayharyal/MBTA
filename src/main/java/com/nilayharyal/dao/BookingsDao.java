package com.nilayharyal.dao;

import java.util.List;


import com.nilayharyal.entity.Bookings;
import com.nilayharyal.entity.User;
import com.nilayharyal.exception.UserException;

public interface BookingsDao {

	public Long availability(String trainName,String travelDate);
	
	public void book(Bookings b) throws UserException;
	
	public void delete(Long id);
	
	public List<Bookings> mybookings(User user);
}
