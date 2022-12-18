package org.nlt.controller.services;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.nlt.include.ProjectUtility;
import org.nlt.listener.DatabaseConnection;
import org.nlt.model.Cities;
import org.nlt.model.Persons;
import org.springframework.stereotype.Service;

@Service
public class PersonServices
{

	public boolean submitPersonService(String name, int age, Long phone, Cities city)
	{
		Persons person=new Persons();
		person.setName(name);
		person.setAge(age);
		person.setPhone(phone);
		person.setCity(city);
		person.setStatus(1);
		person.setCreated(new Date());
		person.setModified(new Date());
		person.setUser(ProjectUtility.loginUser);
		Session ses=DatabaseConnection.getDatabaseSession();
		ses.beginTransaction();
		ses.save(person);
		ses.getTransaction().commit();
		return true;
	}
	
	public List<Persons> getPersonList()
	{
		Session ses=DatabaseConnection.getDatabaseSession();
		ses.beginTransaction();
		Query query = ses.createQuery("from Persons where status=1");
		List list=query.list();
		ses.getTransaction().commit();
		return list;
	}
	
	public Persons getPersonRecord(int id) 
	{
		Session ses=DatabaseConnection.getDatabaseSession();
		ses.beginTransaction();
		Persons person = (Persons) ses.get(Persons.class, id);
		ses.beginTransaction().commit();
		return person;
	}

	public boolean updatePersonService(Persons person)
	{
		Session ses=DatabaseConnection.getDatabaseSession();
		ses.beginTransaction();
		ses.update(person);
		ses.getTransaction().commit();
		return true;
	}

	

	public boolean deletePersonService(Persons person)
	{
		Session ses=DatabaseConnection.getDatabaseSession();
		ses.beginTransaction();
		ses.update(person);
		ses.getTransaction().commit();
		return true;
	}

}
