package com.nilayharyal.controller;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import com.nilayharyal.dao.BookingsDao;
import com.nilayharyal.dao.PaymentDao;
import com.nilayharyal.entity.BankAccount;
import com.nilayharyal.entity.Bookings;
import com.nilayharyal.entity.CreditCard;
import com.nilayharyal.entity.Pdf;
import com.nilayharyal.entity.User;

@Controller
public class bookingsController {
	
	@Autowired
	BookingsDao bookingsDao;

	@Autowired
	PaymentDao paymentDao;
	
//	@Autowired
//	BookingsValidator bookingsValidator;
	
	
	@GetMapping("/printBookings")
	public ModelAndView printBooking(@RequestParam("id")Long id, Model model,HttpServletRequest request) {
		User user = (User)request.getSession().getAttribute("user");
		List<Bookings> list = bookingsDao.mybookings(user);
		for(Bookings b:list) {
			if(b.getBookingsId()==id) {
				model.addAttribute("booking",b);
			}
		}
		model.addAttribute("bookings", list);
		View v = new Pdf();
		return new ModelAndView(v);
		//return "redirect:/mybookings.htm";
	}
	
	@GetMapping("/cancelBookings")
	public String cancelBooking(@RequestParam("id")Long id,HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User)request.getSession().getAttribute("user");
		bookingsDao.delete(id);
		Set<Bookings> mybookings= user.getBookings();
		session.setAttribute("mybookings", mybookings);
		return "redirect:/mybookings.htm";
	}
	
	@RequestMapping(value = "/booktickets.htm", method = RequestMethod.GET)
	public String booktickets(@RequestParam("train") String train, HttpServletRequest request, Locale locale,
			Model model) {
		HttpSession session = request.getSession();
		session.setAttribute("train", train);
		String travel = session.getAttribute("travelDate").toString();
		if (session.getAttribute("user") == null) {
			return "login";
		} else {
			Long availableSeats = bookingsDao.availability(train,travel);
			session.setAttribute("availabeSeats", availableSeats);
			return "booktickets";
		}
	}
	
	@RequestMapping(value = "/payment.htm", method = RequestMethod.POST)
	public ModelAndView makePayment(HttpServletRequest request,Model model) {
		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		List<CreditCard>cards=paymentDao.retrieveCards(user);
		List<BankAccount>accounts=paymentDao.retrieveAccounts(user);
		if(cards !=null) {
			session.setAttribute("cards",cards);
		}
		if(accounts !=null) {
			session.setAttribute("accounts",accounts);
		}
		String passengers = request.getParameter("passengers");
		session.setAttribute("passengers", passengers);
		//validation for passengers
		if (Integer.parseInt(passengers)>bookingsDao.availability(session.getAttribute("train").toString(), session.getAttribute("travelDate").toString())) {
			model.addAttribute("availError","not enough seats avaiable");
			return new ModelAndView("booktickets");	
		}
		String paymentMethod = request.getParameter("payment");
		mv.addObject("passengers", passengers);
		session.setAttribute("passengers", passengers);
		if (paymentMethod.equals("card")) {
			CreditCard creditCard = new CreditCard();
			creditCard = paymentDao. retrieveCard(user);
			if(creditCard == null) {
				mv.addObject("creditCard", new CreditCard());	
			}else {
			creditCard.setOwner(user);
			mv.addObject("creditCard", creditCard);
			}
			mv.setViewName("Cardpayment");
		} else {
			BankAccount bankAccount = new BankAccount();
			bankAccount = paymentDao. retrieveAccount(user);
			if(bankAccount == null) {
				mv.addObject("bankAccount", new BankAccount());	
			}else {
				bankAccount.setOwner(user);
			mv.addObject("bankAccount", bankAccount);
			}
			mv.setViewName("Accountpayment");
		}
		return mv;
	}
	
	@RequestMapping(value = "/loadcard.htm", method = RequestMethod.POST)
	public ModelAndView loadCards(HttpServletRequest request) {
		ModelAndView mv=new ModelAndView();
		HttpSession session=request.getSession();
		String cardNo=request.getParameter("cards");
		User user=(User)session.getAttribute("user");
		List<CreditCard>cards=paymentDao.retrieveCards(user);
		for(CreditCard card:cards) {
			if(card.getCardNumber().equals(cardNo)) {
				mv.addObject("creditCard", card);
				mv.setViewName("Cardpayment");
				return mv;
				}
		}
		return null;
	}
	
	@RequestMapping(value = "/loadaccount.htm", method = RequestMethod.POST)
	public ModelAndView loadAccount(HttpServletRequest request) {
		ModelAndView mv=new ModelAndView();
		HttpSession session=request.getSession();
		String accountNo=request.getParameter("accounts");
		User user=(User)session.getAttribute("user");
		List<BankAccount>accounts=paymentDao.retrieveAccounts(user);
		for(BankAccount ba:accounts) {
			if(ba.getAccountNo().equals(accountNo)) {
				mv.addObject("bankAccount", ba);
				mv.setViewName("Accountpayment");
				return mv;
				}
		}
		return null;
	}
	
	@RequestMapping(value = "/confirmcardbooking", method = RequestMethod.POST)
	public ModelAndView confirmPayment(@ModelAttribute("creditCard") CreditCard creditCard,HttpServletRequest request,Model model) throws EmailException {
//		bookingsValidator.validate(creditCard, bindingResult);
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		// check requested operation by user
				 String operation=request.getParameter("cardOperation");
				 if(operation==null)
				 operation = "a"; // to handle default
				 request.getSession().getAttribute("user");
				 
				 //check for length validation
				 if(creditCard.getCardNumber().length() >12) {
					model.addAttribute("creditCard",creditCard);
					model.addAttribute("lengthError","card number can not exceed 12");
					return new ModelAndView("Cardpayment");	 
				 }
				 
				switch (operation) {
				case "create":
				//check for unique
				CreditCard existing = paymentDao.findBycardNumber(creditCard.getCardNumber());
				if(existing !=null) {
				model.addAttribute("creditCard",creditCard);
				model.addAttribute("saveError","card already exists");
				return new ModelAndView("Cardpayment");
				}
				creditCard.setOwner(user);
				paymentDao.create(creditCard);		
				break;
				case "update":
					if(paymentDao.retrieveCard(user) == null) {
						creditCard.setOwner(user);
						paymentDao.create(creditCard);		
						}else {
						creditCard.setOwner(user);
						paymentDao.update(creditCard);	
						}	
				break;
				case "delete":
					creditCard.setOwner(user);
					paymentDao.deleteCard(creditCard);
					break;
				default:
					break;
				}
				
				// create booking
				Bookings bookings = new Bookings();
				bookings.setArrivalStation(session.getAttribute("origin").toString());
				bookings.setBookedStatus("Booked");
				bookings.setDepartureStation(session.getAttribute("destination").toString());
				bookings.setTickets(Integer.parseInt((String)session.getAttribute("passengers")));
				bookings.setTrainName(session.getAttribute("train").toString());
				String date1= (String)session.getAttribute("travelDate");
				bookings.setTravelDate(date1);
				user.addBooking(bookings);
				paymentDao.createBooking(bookings);
				//send email confirmation to user
				SimpleEmail email = new SimpleEmail();
				email.setHostName("smtp.gmail.com");
				email.setSmtpPort(587);
				email.setAuthenticator(new DefaultAuthenticator("mbta444@gmail.com", "Boston#115"));
				email.setSSLOnConnect(true);
				email.setStartTLSEnabled(true);
				email.setFrom("mbta444@gmail.com");
				email.setSubject("Ticket Booked Succesfully");
		        email.setMsg("Hello "+user.getFirstName()+" "+user.getLastName() +",\n\nYour ticket has been succesfully booked with MBTA\n\n You can see the same from manage bookings.");
		        email.addTo(user.getEmail());
		        email.send();
		return new ModelAndView("redirect:/mybookings.htm");
	}
	
	@RequestMapping(value = "/confirmaccountbooking", method = RequestMethod.POST)
	public ModelAndView confirmPayment(@ModelAttribute("bankAccount") BankAccount bankAccount, HttpServletRequest request,Model model) throws EmailException {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		// check requested operation by user
				 String operation=request.getParameter("accountOperation");
				 if(operation==null)
				 operation = "a"; // to handle default
				 request.getSession().getAttribute("user");
				 
				//check for length validation
				 if(bankAccount.getAccountNo().length() >12) {
					model.addAttribute("bankAccount",bankAccount);
					model.addAttribute("acclengthError","account number can not exceed 12");
					return new ModelAndView("Accountpayment");	 
				 }
				 if(bankAccount.getRoutingNo().length() >12) {
						model.addAttribute("bankAccount",bankAccount);
						model.addAttribute("roulengthError","Routing number can not exceed 12");
						return new ModelAndView("Accountpayment");	 
					 }
				 
				 
				switch (operation) {
				case "create":
					//check for unique
					BankAccount existing = paymentDao.findByAccountNumber(bankAccount.getAccountNo());
					if(existing !=null) {
					model.addAttribute("bankAccount",bankAccount);
					model.addAttribute("saveError","bank account already exists");
					return new ModelAndView("Accountpayment");
					}
					bankAccount.setOwner(user);
					paymentDao.create(bankAccount);	
				break;
				case "update":
					if(paymentDao.retrieveAccount(user) == null) {
						bankAccount.setOwner(user);
					    paymentDao.create(bankAccount);		
					}else {
						bankAccount.setOwner(user);
					paymentDao.update(bankAccount);	
					}
				break;
				case "delete":
					bankAccount.setOwner(user);
					paymentDao.deleteAccount(bankAccount);
					break;
				default:
					break;
				}
				
				// create booking
				Bookings bookings = new Bookings();
				bookings.setArrivalStation(session.getAttribute("origin").toString());
				bookings.setBookedStatus("Booked");
				bookings.setDepartureStation(session.getAttribute("destination").toString());
				bookings.setTickets(Integer.parseInt((String)session.getAttribute("passengers")));
				bookings.setTrainName(session.getAttribute("train").toString());
				String date1= (String)session.getAttribute("travelDate");
				bookings.setTravelDate(date1);
				bookings.setUser(user);
				paymentDao.createBooking(bookings);
				SimpleEmail email = new SimpleEmail();
				email.setHostName("smtp.gmail.com");
				email.setSmtpPort(587);
				email.setAuthenticator(new DefaultAuthenticator("mbta444@gmail.com", "Boston#115"));
				email.setSSLOnConnect(true);
				email.setStartTLSEnabled(true);
				email.setFrom("mbta444@gmail.com");
				email.setSubject("Ticket Booked Succesfully");
		        email.setMsg("Hello "+user.getFirstName()+" "+user.getLastName() +",\n\nYour ticket has been succesfully booked with MBTA\n\n You can see the same from manage bookings.");
		        email.addTo(user.getEmail());
		        email.send();
		return new ModelAndView("redirect:/mybookings.htm");
	}
	
	 public String createCategory(@ModelAttribute("creditCard") CreditCard
	 creditCard, HttpServletRequest request) { String operation =
	 request.getParameter("cardOperation"); switch(operation) { case "add": //
	 paymentDao.deleteCard(creditCard); break; default: break; } return
	 "confirmbooking"; }
	 

	@RequestMapping(value = "/confirmbooking2.htm", method = RequestMethod.POST)
	public ModelAndView createCategory(@ModelAttribute("bankAccount") BankAccount bankAccount,
			HttpServletRequest request) {
		String operation = request.getParameter("accountOperation");
		switch (operation) {
		case "create":
			paymentDao.create(bankAccount);
			break;
		case "delete":
			paymentDao.deleteAccount(bankAccount);
			break;
		default:
			break;
		}
		return new ModelAndView("confirmbooking");
	}
	
	
	@GetMapping("/mybookings.htm")
	public String mybookings(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User)request.getSession().getAttribute("user");
		List<Bookings> mybookings = bookingsDao.mybookings(user); 
		session.setAttribute("mybookings", mybookings);
		return "mybookings";
	}

}
