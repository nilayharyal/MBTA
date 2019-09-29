package com.nilayharyal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.nilayharyal.dao.UserDao;

@Controller
public class HomeController {


	@Autowired
	UserDao userDao;

	@GetMapping("/")
	public String showHomePage() {

		return "home";
	}

	@GetMapping("/home.htm")
	public String showHome() {

		return "home";
	}
	
	@GetMapping("/payment.htm")
	public String payment() {

		return "access-denied";
	}

	@GetMapping("/loadcard.htm")
	public String loadcard() {

		return "access-denied";
	}
	@GetMapping("/loadaccount.htm")
	public String loadaccount() {

		return "access-denied";
	}
	@GetMapping("/signup.htm")
	public String signup() {

		return "registration-form";
	}
	
	@GetMapping("/*")
	public String garbagePage1() {

		return "access-denied";
	}
	@GetMapping("/*/*")
	public String garbagePage2() {

		return "access-denied";
	}
	@GetMapping("/*/*/*")
	public String garbagePage3() {

		return "access-denied";
	}
	
}
