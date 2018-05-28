package com.itktechnologies.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="car")
public class Car extends Vehicle {

	public Car(String model, String description) 
	{
		super(model, description);
	}
}
