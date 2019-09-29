package com.nilayharyal.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Bookings {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bookingsId;
	
	public Long getBookingsId() {
		return bookingsId;
	}

	public void setBookingsId(Long bookingsId) {
		this.bookingsId = bookingsId;
	}

	@Column(name = "trainName")
	private String trainName;
	
	@Column(name = "arrivalStation")
	private String arrivalStation;
	
	@Column(name = "departureStation")
	private String departureStation;
	
	@Column(name ="tickets")
	private int tickets;
	
	@Column(name = "bookedStatus")
	private String bookedStatus;
	
	@Column(name = "travelDate")
	private String travelDate;

	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumn(name="userId")
	private User user;
	
	public String getTrainName() {
		return trainName;
	}

	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}

	public String getArrivalStation() {
		return arrivalStation;
	}

	public void setArrivalStation(String arrivalStation) {
		this.arrivalStation = arrivalStation;
	}

	public String getDepartureStation() {
		return departureStation;
	}

	public void setDepartureStation(String departureStation) {
		this.departureStation = departureStation;
	}

	public int getTickets() {
		return tickets;
	}

	public void setTickets(int tickets) {
		this.tickets = tickets;
	}

	public String getBookedStatus() {
		return bookedStatus;
	}

	public void setBookedStatus(String bookedStatus) {
		this.bookedStatus = bookedStatus;
	}

	public String getTravelDate() {
		return travelDate;
	}

	public void setTravelDate(String travelDate) {
		this.travelDate = travelDate;
	}
	
	
}
