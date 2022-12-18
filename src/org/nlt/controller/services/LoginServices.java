package org.nlt.controller.services;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.nlt.listener.DatabaseConnection;
import org.nlt.model.Users;
import org.springframework.stereotype.Service;

@Service
public class LoginServices
{
	public Users loginUserService(String email, String password) throws Exception
	{
		
		Session ses=DatabaseConnection.getDatabaseSession();
		ses.beginTransaction();
		
		Query query = ses.createQuery("from Users where email='" + email + "' and password='" + password + "' and status=1");
		List<Users> list = query.list();
        ses.getTransaction().commit();
        Iterator<Users> itr = list.iterator();
        if (itr.hasNext())
        {
        	Users user=itr.next();
        	return user;
        }
        else
        {
        	return null;
        }
	}
}
