package com.nilayharyal.dao;

import java.util.List;
import com.nilayharyal.entity.Schedule;
import com.nilayharyal.exception.UserException;

public interface ScheduleDao {

	
	public List<Schedule> list(String origin,String destination) throws UserException;
	
	public void register(Schedule s) throws UserException;

	public void delete(Schedule schedule) throws UserException;
	
	public List<Schedule> trainSchedule(String trainName);
	
	public void update(Schedule s);
	
	public List<String> trains();
	
	public List<String> Stations();
	
	public Schedule getSchedule(long id);
	
	

}
