package org.nlt.controller.services;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.nlt.listener.DatabaseConnection;
import org.nlt.model.Persons;
import org.nlt.model.States;
import org.springframework.stereotype.Service;

@Service
public class StatesServices {
	public List<States> getStatesList()
	{
		Session ses=DatabaseConnection.getDatabaseSession();
		ses.beginTransaction();
		Query query = ses.createQuery("from States where status=1");
		List<States> list = query.list();
		ses.getTransaction().commit();
		return list;
	}
	
	public States getState(String stateName) 
	{
		Session ses=DatabaseConnection.getDatabaseSession();
		ses.beginTransaction();
		Query query = ses.createQuery("from States where status=1 and name='"+stateName+"'");
		List<States> list=query.list();
		ses.getTransaction().commit();
		Iterator<States> itr = list.iterator();
		if(itr.hasNext())
		{
			States state = itr.next();
			return state;
		}
		else
		{
			return null;
		}
	}	
}
