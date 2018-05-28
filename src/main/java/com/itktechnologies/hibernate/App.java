package com.itktechnologies.hibernate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

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
	private static Transaction transaction;
	
    public static void main( String[] args )
    {
    	init();
    		//addInheritance();
    		//add();
    		//search(1);
    		//update(1);
    	
    		//bulkAdd();
    		//query();
    		cache();
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
    
    public static void bulkAdd() {
    	List<Address> userAddress = new ArrayList<>();
    	
    	userAddress.add(new Address("1020 Capstone CT", "Suwanee", "GA", "30024"));
    	
		try {
			session = factory.getCurrentSession();
			
			session.beginTransaction();
				for ( int i = 0; i < 100; i++) {
					session.persist(new UserDetails("user00" + i, new Date(), userAddress, "Auto-generated user" ));
				}				
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

    public static void query() {
    	Query query = null;
    	List<UserDetails> userList = null;
    	int userId = 90;
    	String userName = "user0094";
    	
		try {
			// example 1 - bind variables
			session = factory.getCurrentSession();
			
			session.beginTransaction();
				query = session.createQuery("from UserDetails where userId > :userId and userName = :userName");
				query.setParameter("userId", userId);
				query.setParameter("userName", userName);
				
				query.setFirstResult(0);
				query.setMaxResults(5);
				
				userList = query.list();

				System.out.println("Size of the list....:" + userList.size());
				
				Iterator<UserDetails> iterator = userList.iterator();
				
				while ( iterator.hasNext()) {
					System.out.println(iterator.next().getUserName());
				}
				
			session.getTransaction().commit();
			
			//example 2 - named queries
			session = factory.getCurrentSession();
			session.beginTransaction();
				query = session.getNamedQuery("UserDetails.byId");
				query.setParameter("userId", userId);
				userList = query.list();
				

				System.out.println("Size of the list....:" + userList.size());
				
				iterator = userList.iterator();
				
				while ( iterator.hasNext()) {
					System.out.println(iterator.next().getUserName());
				}				
				
			session.getTransaction().commit();
			
			
			//example 3 - native query
			
			session = factory.getCurrentSession();
			session.beginTransaction();
				query = session.getNamedQuery("UserDetails.getVersion");
				List<String> dbVersion = query.list();
				
				System.out.println("Database Version...:" + dbVersion.get(0));
				System.out.println("Database Class.....:" + dbVersion.get(0).getClass());
				
			session.getTransaction().commit();
			
			//example 4 - criteria
			// https://www.boraji.com/hibernate-5-criteria-query-example
			
			session = factory.getCurrentSession();
			session.beginTransaction();
				CriteriaBuilder builder = session.getCriteriaBuilder();
				CriteriaQuery<UserDetails> criteriaQuery = builder.createQuery(UserDetails.class);
				
				Root<UserDetails> root = criteriaQuery.from(UserDetails.class);
				criteriaQuery.select(root).where(builder.equal(root.get("userId"), 99));
				
				Query<UserDetails> q = session.createQuery(criteriaQuery);
				
				List<UserDetails> list = q.getResultList();
				
				iterator = list.iterator();
				
				while ( iterator.hasNext()) {
					System.out.println(iterator.next().getUserName());
				}
				
				System.out.println(list);
				
			
			session.getTransaction().commit();
			
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}		
    	
    }

    public static void cache() {
    	// https://www.journaldev.com/2980/hibernate-ehcache-hibernate-second-level-cache
    	
    	UserDetails user;
    	
    	try {
    		session = factory.openSession();
				user = session.get(UserDetails.class, 1);			
				//System.out.println(user);
			
		    
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		
    		//if ( transaction != null) {
    		//	transaction.rollback();
    		//}
    	} finally {
    		session.close();
    	}
    	
    	
    	try {
    		session = factory.openSession();
				user = session.get(UserDetails.class, 1);			
				//System.out.println(user);
			
		    
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		
    		//if ( transaction != null) {
    		//	transaction.rollback();
    		//}
    	} finally {
    		session.close();
    	}
    	
    	
    }
}
