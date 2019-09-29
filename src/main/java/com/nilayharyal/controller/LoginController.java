package com.nilayharyal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	@GetMapping("/login.htm")
	public String userLoginPage() {
	return "login";
		
	}
	
	@GetMapping("/access-denied")
	public String AccessDeniedPage() {
	return "access-denied";
		
	}
	
}









