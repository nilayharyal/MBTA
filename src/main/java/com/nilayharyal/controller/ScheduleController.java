package com.nilayharyal.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
//import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.nilayharyal.dao.ScheduleDao;
import com.nilayharyal.entity.Schedule;
import com.nilayharyal.exception.UserException;

@Controller
public class ScheduleController {

	@Autowired
	ScheduleDao scheduleDao;
	
	
	@GetMapping("/addSchedule.htm")
	public ModelAndView addSchedule() {
		ModelAndView mv=new ModelAndView();
		mv.addObject("schedule", new Schedule());
		mv.setViewName("addSchedule");
		return mv;
	}
	
	@GetMapping("/updateschedule.htm")
	public ModelAndView addSchedule(@RequestParam("id")Long id,HttpServletRequest request) {
		ModelAndView mv=new ModelAndView();
		if(id==null) {
		mv.addObject("schedule", new Schedule());
		}else {
			HttpSession session=request.getSession();
			session.setAttribute("scheduleId", id);
			Schedule schedule=scheduleDao.getSchedule(id);
			mv.addObject("schedule",schedule);
		}
		mv.setViewName("addSchedule");
		return mv;
	}
	
	@GetMapping("/deleteSchedule.htm")
	public ModelAndView deleteSchedule(@RequestParam("id")Long id,HttpServletRequest request) {
			Schedule schedule=scheduleDao.getSchedule(id);
			try {
				scheduleDao.delete(schedule);
			} catch (UserException e) {
				e.printStackTrace();
			}
			return new ModelAndView("modifySchedule");
	
	}
	
	@GetMapping("/modifySchedule.htm")
	public String modifySchedule(HttpServletRequest request) {
			HttpSession session = request.getSession();
			List<String> trains = scheduleDao.trains();
			session.setAttribute("trains", trains);
			try {
			session.removeAttribute("trainName");
			session.removeAttribute("trainSchedule");
			}catch(Exception e) {
				//do nothing
			}
		return "modifySchedule";
	}
	


	
	@RequestMapping(value = "/schedule.htm", method = RequestMethod.POST)
	public String scheduleLoad(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String trainName = request.getParameter("trainName");
		session.setAttribute("trainName", trainName);
		session.setAttribute("trainSchedule", scheduleDao.trainSchedule(trainName));
		return "schedule";
	}

	@RequestMapping(value = "/schedule.htm", method = RequestMethod.GET)
	public String schedule(HttpServletRequest request) {
		HttpSession session = request.getSession();
		List<String> trains = scheduleDao.trains();
		session.setAttribute("trains", trains);
		try {
		session.removeAttribute("trainName");
		session.removeAttribute("trainSchedule");
		}catch(Exception e) {
			//do nothing
		}
		return "schedule";
	}
	
	@RequestMapping(value = "/addschedule.htm", method = RequestMethod.POST)
	public ModelAndView addNewSchedule(@ModelAttribute("schedule") Schedule schedule,HttpServletRequest request) throws UserException {
					HttpSession session = request.getSession();
			Long trainId = (Long)session.getAttribute("scheduleId");
			if(trainId==null) {
			scheduleDao.register(schedule);
			}else {
			schedule.setTrainId(trainId);	
			scheduleDao.update(schedule);
			session.removeAttribute("scheduleId");
			
			}
			return new ModelAndView("redirect:/modifySchedule.htm");
		}
	
	
	@RequestMapping(value = "/modifyschedule.htm", method = RequestMethod.POST)
	public ModelAndView loadSchedule(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession();
		String trainName = request.getParameter("trainName");
		session.setAttribute("trainName", trainName);
		session.setAttribute("trainSchedule", scheduleDao.trainSchedule(trainName));
		mv.addObject("scheduleList", scheduleDao.trainSchedule(trainName));
		mv.setViewName("modifySchedule");
		return mv;
	}
	
	@RequestMapping(value = "/newbookings.htm", method = RequestMethod.POST)
	public String newbookings(HttpServletRequest request) throws UserException {
		List<Schedule> schedules = new ArrayList<Schedule>();
		HttpSession session = request.getSession();
		session.setAttribute("travelDate", request.getParameter("travelDate"));
		String origin = request.getParameter("originStation");
		String destination = request.getParameter("destinationStation");
		schedules = scheduleDao.list(origin, destination);
		session.setAttribute("schedules", schedules);
		session.setAttribute("origin", origin);
		session.setAttribute("destination", destination);
		return "newbookings";
	}
	
	@GetMapping("/newbookings.htm")
	public String newbooking(HttpServletRequest request) {
		List<String>stations = scheduleDao.Stations();
		HttpSession session = request.getSession();
		session.setAttribute("stations", stations);
		return "newbookings";
	}

}
