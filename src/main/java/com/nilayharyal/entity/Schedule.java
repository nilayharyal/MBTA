package com.nilayharyal.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Schedule {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long trainId;

@Column(name = "trainName")
private String trainName;

@Column(name = "arrival")
private String arrival;

@Column(name = "departure")
private String departure;


@Column(name = "station")
private String Station;

@Column(name = "delay")
private int delay;

public String getTrainName() {
	return trainName;
}

public Long getTrainId() {
	return trainId;
}

public void setTrainId(Long trainId) {
	this.trainId = trainId;
}

public void setTrainName(String trainName) {
	this.trainName = trainName;
}

public String getArrival() {
	return arrival;
}

public void setArrival(String arrival) {
	this.arrival = arrival;
}

public String getDeparture() {
	return departure;
}

public void setDeparture(String departure) {
	this.departure = departure;
}



public String getStation() {
	return Station;
}

public void setStation(String station) {
	Station = station;
}

public int getDelay() {
	return delay;
}

public void setDelay(int delay) {
	this.delay = delay;
}

}
