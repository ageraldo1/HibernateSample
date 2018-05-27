package com.itktechnologies.entity;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="projects")
public class Project {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String description;
	private Date start;
	private Date end;
	
	@ManyToMany(mappedBy="project")
	private Collection<UserDetails> users = new HashSet<>();
	
	public Project(String description, Date start, Date end) {
		super();
		this.description = description;
		this.start = start;
		this.end = end;
	}
	
	
	
	public Project(String description, Date start, Date end, Collection<UserDetails> users) {
		super();
		this.description = description;
		this.start = start;
		this.end = end;
		this.users = users;
	}



	public Project()
	{
		super();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public Collection<UserDetails> getUsers() {
		return users;
	}

	public void setUsers(Collection<UserDetails> users) {
		this.users = users;
	}
	
	
	
}
