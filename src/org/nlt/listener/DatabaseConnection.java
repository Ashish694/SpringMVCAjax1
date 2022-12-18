package org.nlt.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;

public class DatabaseConnection implements ServletContextListener 
{
	private static Session ses;
	private static SessionFactory sf;
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("Context Initialized");
		
		sf=new Configuration().configure("org/nlt/listener/hibernate.cfg.xml").buildSessionFactory();
		ses=sf.openSession();
		System.out.println("Connectivity Ready You Can Use It");
	}
	public void contextDestroyed(ServletContextEvent sce) 
	{
		System.out.println("Context Destroyed");
		if(ses!=null)
		{
			ses.close();
		}
		
		if(sf!=null)
		{
			sf.close();
		}
		System.out.println("Session Destroyed");
	}
	
	public static Session getDatabaseSession()
	{
		return ses;
	}
}
