package com.nilayharyal.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nilayharyal.entity.Schedule;
import com.nilayharyal.exception.UserException;

@Repository
public class ScheduleDaoImpl implements ScheduleDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public List<Schedule> list(String origin,String destination) throws UserException{
		try {
			// get the current hibernate session
			Session currentSession = sessionFactory.getCurrentSession();
			DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
			
			@SuppressWarnings("deprecation")
			Criteria cr = currentSession.createCriteria(Schedule.class);
			Criterion originCondition =Restrictions.ilike("Station",origin,MatchMode.ANYWHERE);
			Criterion destinationCondition =Restrictions.ilike("Station",destination,MatchMode.ANYWHERE);
			LogicalExpression orExp = Restrictions.or(originCondition,destinationCondition);
			cr.add(orExp);
			cr.addOrder(Order.desc("trainName"));
			cr.addOrder(Order.desc("arrival"));
			
			List<Schedule> schedules = cr.list();
			
			List<Schedule> results = new ArrayList<Schedule>();
			Date originTime=null;
			Date destinationTime=null;
			Schedule originstation=null;
			Schedule destinationstation=null;
			
			for (Schedule schedule: schedules) {
			
				if(schedule.getStation().equals(origin)) {
					try {
						originTime = dateFormat.parse(schedule.getArrival());
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					originstation=schedule;
				}
				
				if(schedule.getStation().equals(destination)) {
					try {
						destinationTime = dateFormat.parse(schedule.getArrival());
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					destinationstation=schedule;
				}
				
				if(originTime!=null && destinationTime!=null) {
					if(originTime.before(destinationTime)) {
						if(originstation.getTrainName().equals(destinationstation.getTrainName())) {
						results.add(originstation);
						results.add(destinationstation);
						}
						 originTime=null;
						 destinationTime=null;
						 originstation=null;
						 destinationstation=null;
					}
					
				}
				
			}
			return results;
		} catch (HibernateException e) {
			throw new UserException("Could not get schedule", e);
		}
	}

	@Override
	@Transactional
	public void register(Schedule s) throws UserException {
		try {
			Session currentSession = sessionFactory.getCurrentSession();
			currentSession.save(s);
		} catch (HibernateException e) {
			throw new UserException("Exception while adding Schedule: " + e.getMessage());
		}
	}
	
	@Override
	@Transactional
	public void delete(Schedule schedule) throws UserException {
		try {
			Session currentSession = sessionFactory.getCurrentSession();
			currentSession.delete(schedule);
		} catch (HibernateException e) {
			throw new UserException("Could not delete Schedule ", e);
		}
	}
	
	@Override
	@Transactional
	public List<Schedule> trainSchedule(String trainName) {
		Session currentSession = sessionFactory.getCurrentSession();
		Criteria cr = currentSession.createCriteria(Schedule.class);
		cr.add(Restrictions.eq("trainName",trainName));
		List<Schedule> result = cr.list();
		return cr.list();
	}

	@Transactional
	public List<String> trains() {
		Session currentSession = sessionFactory.getCurrentSession();
		Query query = currentSession.createQuery("select DISTINCT s.trainName from Schedule s");
		List<String> result = query.list();
		return result;
	}
	
	@Transactional
	public List<String> Stations() {
		Session currentSession = sessionFactory.getCurrentSession();
		Query query = currentSession.createQuery("select DISTINCT s.Station from Schedule s");
		List<String> result = query.list();
		return result;
	}

	@Transactional
	public Schedule getSchedule(long id) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query query =currentSession.createQuery("from Schedule where trainId=:trainId");
		query.setLong("trainId", id);
		Schedule schedule=(Schedule)query.uniqueResult();
		return schedule;
	}

	@Transactional
	public void update(Schedule s) {
		Session currentSession = sessionFactory.getCurrentSession();
		String hql="update Schedule set trainName=:trainName, arrival=:arrival,departure=:departure,Station=:Station,delay=:delay where id=:owner_id";
		Query query = currentSession.createQuery(hql);
		query.setString("trainName", s.getTrainName());
		query.setString("arrival", s.getArrival());
		query.setString("departure", s.getDeparture());
		query.setString("Station", s.getStation());
		query.setInteger("delay", s.getDelay());
		query.setLong("owner_id", s.getTrainId());
		int result = query.executeUpdate();
		
	}
	
	
}
