package org.nlt.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nlt.controller.services.LoginServices;
import org.nlt.controller.services.PersonServices;
import org.nlt.controller.services.StatesServices;
import org.nlt.include.ProjectUtility;
import org.nlt.model.Persons;
import org.nlt.model.States;
import org.nlt.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController
{
	@Autowired
	private LoginServices loginService;
	
	@Autowired
	private PersonServices personService;
	
	@Autowired
	private StatesServices statesService;
	
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public ModelAndView login()
	{
		return new ModelAndView("login");
		
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public ModelAndView loginUser(HttpServletRequest req, HttpServletResponse res) throws Exception
	{
		HashMap m=new HashMap();
		String email=req.getParameter("email");
		String password=req.getParameter("password");
		
		
			Users user=loginService.loginUserService(email, password);
			if(user!=null)
			{
				List<Persons> personList=personService.getPersonList();
				List<States> statesList=statesService.getStatesList();
				
				ProjectUtility.loginUser=user;
				m.put("personList", personList);	
				m.put("statesList", statesList);	
				
				m.put("action", "./submitPerson");
				m.put("button", "SUBMIT");
				return new ModelAndView("index",m);
			}
			else
			{
				m.put("error", "INVALID USERNAME OR PASSWORD.");
				return new ModelAndView("login",m);
			}
		
	}
}
