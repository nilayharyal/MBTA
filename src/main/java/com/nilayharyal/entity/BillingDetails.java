package com.nilayharyal.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class BillingDetails {

@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
private Long id;

@ManyToOne
@JoinColumn(name="user_Id")
private User owner;

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public User getOwner() {
	return owner;
}

public void setOwner(User owner) {
	this.owner = owner;
}


	
}
