package com.itktechnologies.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="vehicle")

public class Vehicle {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String model;
	private String description;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private UserDetails user;
	
	public Vehicle () {
		super();
	}
	
	public Vehicle(String model, String description) {
		super();
		
		this.model = model;
		this.description = description;
	}
	
	
	public Vehicle(String model, String description, UserDetails user) {
		super();
		this.model = model;
		this.description = description;
		this.user = user;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public UserDetails getUser() {
		return user;
	}

	public void setUser(UserDetails user) {
		this.user = user;
	}
	
	
}
