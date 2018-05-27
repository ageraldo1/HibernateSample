package com.itktechnologies.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name="user_details")
public class UserDetails {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int userId;
	
	@Column(name="usr_name")
	private String userName;
	
	@Column(name="usr_created")
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	
	@Column(name="usr_description")
	private String description;
	
	@Transient
	private String ignoreColumn;
	
	@ElementCollection(fetch=FetchType.EAGER)
	@JoinTable(name="address", joinColumns=@JoinColumn(name="usr_id"))
	private List<Address> userAddresses = new ArrayList<>(); 

	@OneToOne
	@JoinColumn(name="role_id")
	private UserRole role;
	
	@OneToMany(mappedBy="user")
	//@JoinTable(name="user_vehicle", joinColumns=@JoinColumn(name="user_id"),inverseJoinColumns=@JoinColumn(name="vehicle_id"))

	private Set<Vehicle> vehicle = new HashSet<>();	
	
	@ManyToMany()
	@JoinTable(name="usr_projects")	
	private Collection<Project> project = new HashSet<>();

	public UserDetails(String userName, Date created, List<Address> address, String description, UserRole role, Set<Vehicle> vehicle) {
		super();
		this.userName = userName;
		this.created = created;
		this.description = description;
		this.userAddresses = address;
		this.role = role;
		this.vehicle = vehicle;
	}
	

	public UserDetails(String userName, Date created, List<Address> userAddresses, String description ) {
		super();
		this.userName = userName;
		this.created = created;
		this.description = description;
		this.userAddresses = userAddresses;
	}

	public UserDetails() {
		super();
	}

	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public List<Address> getUserAddresses() {
		return userAddresses;
	}

	public void setUserAddresses(List<Address> userAddresses) {
		this.userAddresses = userAddresses;
	}

	@Override
	public String toString() {
		return "UserDetails [userId=" + userId + ", userName=" + userName + ", created=" + created + ", description="
				+ description + ", ignoreColumn=" + ignoreColumn + ", userAddresses=" + userAddresses + "]";
	}

	public Set<Vehicle> getVehicle() {
		return vehicle;
	}

	public void setVehicle(Set<Vehicle> vehicle) {
		this.vehicle = vehicle;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole user_role) {
		this.role = user_role;
	}


	public Collection<Project> getProject() {
		return project;
	}


	public void setProject(Collection<Project> project) {
		this.project = project;
	}
	
	
}
