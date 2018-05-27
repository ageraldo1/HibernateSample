package com.itktechnologies.hibernate;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.itktechnologies.entity.UserDetails;

public class App 
{
    public static void main( String[] args )
    {
    	UserDetails user = new UserDetails("ageraldo", new Date(), "1020 Capstone CT", "Alex Geraldo" );
    	
    	SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(UserDetails.class)
				.buildSessionFactory();
		
		Session session = factory.getCurrentSession();
		
		try {
			session.beginTransaction();
				session.save(user);
			session.getTransaction().commit();
			
		} catch (Exception ex) {
			ex.printStackTrace();
			
		} finally {
			session.close();
			factory.close();
		}
    }
}
