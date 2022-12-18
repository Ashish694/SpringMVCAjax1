package org.nlt.controller.services;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.nlt.listener.DatabaseConnection;
import org.nlt.model.Cities;
import org.nlt.model.Persons;
import org.nlt.model.States;
import org.springframework.stereotype.Service;

@Service
public class CityServices {
	public List<States> getStatesList()
	{
		Session ses=DatabaseConnection.getDatabaseSession();
		ses.beginTransaction();
		Query query = ses.createQuery("from States where status=1");
		List<States> list = query.list();
		ses.getTransaction().commit();
		return list;
	}
	
	public Cities getCity(String cityName) 
	{
		Session ses=DatabaseConnection.getDatabaseSession();
		ses.beginTransaction();
		Query query = ses.createQuery("from Cities where status=1 and name='"+cityName+"'");
		List<Cities> list=query.list();
		ses.getTransaction().commit();
		Iterator<Cities> itr = list.iterator();
		if(itr.hasNext())
		{
			Cities city = itr.next();
			return city;
		}
		else
		{
			return null;
		}
	}	
}
