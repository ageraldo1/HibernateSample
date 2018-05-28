package com.itktechnologies.hibernate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.itktechnologies.entity.Address;
import com.itktechnologies.entity.Bike;
import com.itktechnologies.entity.Car;
import com.itktechnologies.entity.Project;
import com.itktechnologies.entity.UserDetails;
import com.itktechnologies.entity.UserRole;
import com.itktechnologies.entity.Vehicle;

import java.util.Iterator;

public class App 
{
	private static SessionFactory factory;
	private static Session session;
	
    public static void main( String[] args )
    {
    	init();
    		//addInheritance();
    		add();
    		search(1);
    		update(1);
    	shutdown();
    }

    public static void init() {
    	
    	factory = new Configuration()
				.configure("hibernate.cfg.xml")
				//.addAnnotatedClass(UserDetails.class)
				//.addPackage("com.itktechnologies.entity")
				.buildSessionFactory();
    }

    public static void shutdown() {
    	try {
    		if (session.isOpen()) session.close();    		
    		if (factory.isOpen()) factory.close();
    	} catch (Exception ex) {}
    }
    
    public static void add() {

    	List<Address> userAddress = new ArrayList<>();
    	
    	userAddress.add(new Address("1020 Capstone CT", "Suwanee", "GA", "30024"));
    	UserRole userRole = new UserRole("System Administrator");
    	UserDetails user = new UserDetails("ageraldo", new Date(), userAddress, "Alex Geraldo" );

    	user.getVehicle().add(new Vehicle("Honda", "CRV", user));
    	user.getVehicle().add(new Vehicle("Honda", "Fit", user));
    	user.getVehicle().add(new Vehicle("Honda", "Civic", user));
    	user.getVehicle().add(new Vehicle("Honda", "Accord", user));
    	
    	Project userProject = new Project("Infor10 migration", new Date(), new Date());
    	userProject.getUsers().add(user);
    	
    	user.setRole(userRole);
    	user.getProject().add(userProject);

    	
		try {
			session = factory.getCurrentSession();
			
			session.beginTransaction();
//				session.save(userRole);
//				
//				Iterator<Vehicle> iterator = user.getVehicle().iterator();
//				
//				while (iterator.hasNext()) {
//					session.save(iterator.next());
//				}
//
//				session.save(userProject);
//				session.save(user);
				session.persist(user);
			session.getTransaction().commit();
			
		} catch (Exception ex) {
			ex.printStackTrace();
			
			session.getTransaction().rollback();
		}
    }
    
    public static void search(int id) {
    	UserDetails user;
    	
    	try {
    		session = factory.getCurrentSession();
			session.beginTransaction();
				user = session.get(UserDetails.class, id);
				
				if ( user != null ) {
					System.out.print("\n\n\n");
					System.out.println(user);
					System.out.println(user.getClass());
					System.out.println(user.getUserAddresses().size());
					System.out.print("\n\n\n");
					
				} else { 
					System.out.println("User id " + id + " not found on the database");
				}
				
		    session.getTransaction().commit();
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    }

    public static void addInheritance()
    {
    	Vehicle base = new Vehicle("Generic", "Base vehicle");
    	Vehicle car = new Car("Honda", "CRV");
    	Vehicle bike = new Bike("Honda", "CB-450");
    	

    	try {
			session = factory.getCurrentSession();
			
			session.beginTransaction();
				session.save(base);
				session.save(car);
				session.save(bike);
			session.getTransaction().commit();
		
		} catch (Exception ex) {
			ex.printStackTrace();
			
			session.getTransaction().rollback();
		}
    	
    }

    public static void update(int id) {
    	UserDetails user;
    	
    	try {
    		session = factory.getCurrentSession();
			session.beginTransaction();
				user = session.get(UserDetails.class, id);
				
				if ( user != null ) {
					user.setDescription("CRUD update");
					
				} else { 
					System.out.println("User id " + id + " not found on the database");
				}
				
				session.persist(user);
		    session.getTransaction().commit();
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    	
    }
}
