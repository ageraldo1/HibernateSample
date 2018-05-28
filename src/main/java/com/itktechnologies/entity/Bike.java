package com.itktechnologies.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="bike")
public class Bike extends Vehicle{

	public Bike(String model, String description) 
	{
		super(model, description);
	}
	
}
