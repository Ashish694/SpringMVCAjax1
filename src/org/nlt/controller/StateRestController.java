package org.nlt.controller;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.nlt.controller.services.StatesServices;
import org.nlt.listener.DatabaseConnection;
import org.nlt.model.Cities;
import org.nlt.model.States;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StateRestController 
{
	
	@Autowired
	private StatesServices stateService;
	
	@RequestMapping(value="/cityList",method=RequestMethod.GET)
	public List<Cities> getCityList(@RequestParam String name)		
	{
		System.out.println("Hello City List"+name);
		
		States state = stateService.getState(name);
		
		Session ses = DatabaseConnection.getDatabaseSession();
		ses.beginTransaction();
		Query query = ses.createQuery("from Cities where status=1 and state_id="+state.getId());
		List<Cities> cityList = query.list();
		ses.getTransaction().commit();
		return cityList;
	}
}
