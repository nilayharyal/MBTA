package com.nilayharyal.entity;

import javax.persistence.*;

import com.nilayharyal.entity.BillingDetails;
import com.nilayharyal.entity.Bookings;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "email")
	private String email;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "users_roles", 
	joinColumns = @JoinColumn(name = "user_id"), 
	inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Collection<Role> roles;

	
	@OneToMany(fetch = FetchType.EAGER, orphanRemoval=true,cascade = {CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.DETACH, CascadeType.REFRESH},mappedBy="user")
	private Set<Bookings>bookings;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL,mappedBy="owner")
	private Set<BillingDetails> billingDetails;
	
	
	
	public Set<Bookings> getBookings() {
		return bookings;
	}

	public void setBookings(Set<Bookings> bookings) {
		this.bookings = bookings;
	}
	
	public User() {
	}

	public User(String username, String password, String firstName, String lastName, String email) {
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public User(String username, String password, String firstName, String lastName, String email,
			Collection<Role> roles) {
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.roles = roles;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getusername() {
		return username;
	}

	public Set<BillingDetails> getBillingDetails() {
		return billingDetails;
	}

	public void setBillingDetails(Set<BillingDetails> billingDetails) {
		this.billingDetails = billingDetails;
	}

	public void setusername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	public void addBooking(Bookings b) {
		if(bookings == null) {
			bookings = new HashSet<Bookings>();
		}
		bookings.add(b);
		b.setUser(this);
	}
	
	@Override
	public String toString() {
		return "User{" + "id=" + id + ", username='" + username + '\'' + ", password='" + "*********" + '\''
				+ ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", email='" + email + '\''
				+ ", roles=" + roles + '}';
	}
}
