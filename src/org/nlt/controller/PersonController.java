package org.nlt.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nlt.controller.services.CityServices;
import org.nlt.controller.services.PersonServices;
import org.nlt.controller.services.StatesServices;
import org.nlt.model.Cities;
import org.nlt.model.Persons;
import org.nlt.model.States;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PersonController 
{
	@Autowired
	private PersonServices personService;
	
	@Autowired
	private CityServices cityService;
	
	@Autowired
	private StatesServices stateService;
	
	@RequestMapping(value="/submitPerson", method=RequestMethod.POST)
	public ModelAndView submitPerson(HttpServletRequest req, HttpServletResponse res)
	{
		HashMap m= new HashMap();
		
		String name=req.getParameter("name");
		String age=req.getParameter("age");
		String phone=req.getParameter("phone");
		String city=req.getParameter("city");
		String state=req.getParameter("state");
		
		m.put("nameValue", name);
		m.put("ageValue", age);
		m.put("phoneValue", phone);
		m.put("cityValue", city);
		m.put("stateValue", state);
		
		String msg="";
		if(name.isEmpty())
		{
			msg="ENTER YOUR NAME";
			m.put("error", msg);
		}
		else if(age.isEmpty())
		{
			msg="ENTER YOUR AGE";
			m.put("error", msg);
		}
		else if(phone.isEmpty())
		{
			msg="ENTER YOUR PHONE";
			m.put("error", msg);
		}
		else if(city==null)
		{
			msg="SELECT YOUR CITY";
			m.put("error", msg);
		}
		else if(state==null)
		{
			msg="ENTER YOUR STATE";
			m.put("error", msg);
		}
		else
		{
			Cities cityObject = cityService.getCity(city);
			
			boolean result=personService.submitPersonService(name, Integer.parseInt(age), Long.parseLong(phone), cityObject);
			if(result)
			{
				msg="PERSON DETAILS SUBMITTED SUCCESSFULLY";
				m.put("success", msg);
				m.put("action", "./submitPerson");
				m.put("button", "SUBMIT");
				List<Persons> personList=personService.getPersonList();
				m.put("personList", personList);
				
				List<States> statesList=stateService.getStatesList();
				m.put("stateList", statesList);
				
				m.put("nameValue", "");
				m.put("ageValue", "");
				m.put("phoneValue", "");
				m.put("cityValue", "");
				m.put("stateValue", "");
				return new ModelAndView("index",m);
			}
			else
			{
				msg="CAN NOT SUBMIT PERSON DETAILS";
				m.put("error", msg);
				m.put("action", "./submitPerson");
				m.put("button", "SUBMIT");
				return new ModelAndView("index",m);
			}
		}
		
		m.put("action", "./submitPerson");
		m.put("button", "SUBMIT");
		return new ModelAndView("index",m);
	}
	
	@RequestMapping(value="/getPersonEdit", method=RequestMethod.GET)
	public ModelAndView getPersonDetails(HttpServletRequest req, HttpServletResponse res) throws Exception
	{
		String id=req.getParameter("id");
		Persons person=personService.getPersonRecord(Integer.parseInt(id));
		HashMap m=new HashMap();
		m.put("nameValue", person.getName());
		m.put("ageValue", person.getAge());
		m.put("phoneValue", person.getPhone());
		m.put("cityValue", person.getCity());
	//	m.put("stateValue", person.getState());
		m.put("idValue", person.getId());
		m.put("action", "./editPerson");
		m.put("button", "UPDATE");
		List<Persons> personList=personService.getPersonList();
		m.put("personList", personList);
		return new ModelAndView("index",m);
	}
	
	@RequestMapping(value="/editPerson", method=RequestMethod.POST)
	public ModelAndView updatePersonDetails(HttpServletRequest req,HttpServletResponse res) throws Exception
	{
		HashMap m= new HashMap();
		
		String id=req.getParameter("pid");
		String name=req.getParameter("name");
		String age=req.getParameter("age");
		String phone=req.getParameter("phone");
		String city=req.getParameter("city");
		String state=req.getParameter("state");
		
		m.put("nameValue", name);
		m.put("ageValue", age);
		m.put("phoneValue", phone);
		m.put("cityValue", city);
		m.put("stateValue", state);
		
		String msg="";
		if(name.isEmpty())
		{
			msg="PLEASE ENTER YOUR NAME";
			m.put("error", msg);
		}
		else if(age.isEmpty())
		{
			msg="PLEASE ENTER YOUR AGE";
			m.put("error", msg);
		}
		else if(phone.isEmpty())
		{
			msg="PLEASE ENTER YOUR PHONE NO";
			m.put("error", msg);
		}
		else
		{ 
			Persons person=personService.getPersonRecord(Integer.parseInt(id));
			person.setName(name);
			person.setAge(Integer.parseInt(age));
			person.setPhone(Long.parseLong(phone));
			//person.setCity(city);
			//person.setState(state);
			boolean result=personService.updatePersonService(person);
			if(result)
			{
				m.put("success", "PERSON DETAILS UPDATED SUCCESSFULLY.");
				m.put("nameValue", "");
				m.put("ageValue", "");
				m.put("phoneValue", "");
				m.put("cityValue", "");
				m.put("stateValue", "");
				List<Persons> personList=personService.getPersonList();
				m.put("personList", personList);
			}
			else 
			{
				m.put("error", "CAN NOT UPDATE PERSON DETAILS.");
			}
		}
		List<Persons> personList=personService.getPersonList();
		m.put("PersonList", personList);
		m.put("action", "./submitPerson");
		m.put("button", "SUBMIT");
		return new ModelAndView("index",m);
	}
	
	@RequestMapping(value="/getPersonDelete", method=RequestMethod.GET)
	public ModelAndView getPersonData(HttpServletRequest req, HttpServletResponse res) throws Exception
	{
		String id=req.getParameter("id");
		Persons person=personService.getPersonRecord(Integer.parseInt(id));
		HashMap m=new HashMap();
		m.put("nameValue", person.getName());
		m.put("ageValue", person.getAge());
		m.put("phoneValue", person.getPhone());
		m.put("cityValue", person.getCity());
		//m.put("stateValue", person.getState());
		m.put("idValue", person.getId());
		m.put("action", "./deletePerson");
		m.put("button", "DELETE");
		List<Persons> personList=personService.getPersonList();
		m.put("personList", personList);
		return new ModelAndView("index",m);
	}
	
	@RequestMapping(value="/deletePerson", method=RequestMethod.POST)
	public ModelAndView deletePersonDetails(HttpServletRequest req,HttpServletResponse res) throws Exception
	{
		HashMap m= new HashMap();
		
		String id=req.getParameter("pid");
		String name=req.getParameter("name");
		String age=req.getParameter("age");
		String phone=req.getParameter("phone");
		String city=req.getParameter("city");
		String state=req.getParameter("state");
		
		m.put("nameValue", name);
		m.put("ageValue", age);
		m.put("phoneValue", phone);
		m.put("cityValue", city);
		m.put("stateValue", state);
		
		String msg="";
		
			Persons person=personService.getPersonRecord(Integer.parseInt(id));
			person.setStatus(0);
			boolean result=personService.deletePersonService(person);
			if(result)
			{
				m.put("success", "PERSON DETAILS DELETED SUCCESSFULLY.");
				m.put("nameValue", "");
				m.put("ageValue", "");
				m.put("phoneValue", "");
				m.put("cityValue", "");
				m.put("stateValue", "");
				List<Persons> personList=personService.getPersonList();
				m.put("personList", personList);
				m.put("action", "./submitPerson");
				m.put("button", "SUBMIT");
				return new ModelAndView("index",m);
			}
			else 
			{
				m.put("error", "CAN NOT UPDATE PERSON DETAILS.");
			}
		
		List<Persons> personList=personService.getPersonList();
		m.put("PersonList", personList);
		m.put("action", "./submitPerson");
		m.put("button", "SUBMIT");
		return new ModelAndView("index",m);
	}
}
