package com.itktechnologies.hibernate;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.itktechnologies.entity.UserDetails;

public class App 
{
	private static SessionFactory factory;
	private static Session session;
	
    public static void main( String[] args )
    {
    	init();
    		add();
    		search(1);
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
    		session.close();
    		factory.close();
    	} catch (Exception ex) {}
    }
    
    public static void add() {
    	UserDetails user = new UserDetails("ageraldo", new Date(), "1020 Capstone CT", "Alex Geraldo" );
    	
		try {
			session = factory.getCurrentSession();
			
			session.beginTransaction();
				session.save(user);
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
					System.out.println(user);
					
				} else { 
					System.out.println("User id " + id + " not found on the database");
				}
				
		    session.getTransaction().commit();
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    }
}
