<%@page import="org.nlt.include.ProjectUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="<c:url value="/res/css/bootstrap.css"/>"/>
<link rel="stylesheet" href="<c:url value="/res/css/style.css"/>"/>
<script type="text/javascript" src="<c:url value="/res/js/jquery.js"/>"></script>
<title>INDEX PAGE</title>
</head>
<script type="text/javascript">
	$(document).ready(function() {
		
		$("#state").change(function() {
		
		var stateName=$(this).val();
			if(stateName!="Select")
			{
				$.ajax({
					url:"./cityList?name="+stateName,
					type:"GET",
					contentType:"application/json",
					success: function(result)
					{
						addCityData(result);
					},
					error:function(data)
					{
						alert("Error");
					}
					
				});//ajax
			}//if
		});
	});
	
	function addCityData(result)
	{
		var cityCombo = document.getElementById('city');
		
		//create new option element
		
		clearSelectList(cityCombo);//will remove all existing element from combo
		
		for(var i=0;i<result.length;i++)
		{
			var opt = document.createElement('option');
			opt.appendChild( document.createTextNode(result[i].name) );
			opt.value = result[i].name;
			cityCombo.appendChild(opt);
		}
	}
	
	function clearSelectList(list) 
	{
		while (list.options.length) {
			list.remove(0);
		}
	}
</script>

<body>
	<div class="container-fluid">
		<div class="row" style="margin-top:20px">
			<div class="col-md-4" style="float:left">
				<h4>LOGIN: <%=ProjectUtility.loginUser.getName() %></h4>
			</div>
			<div class="col-md-4"></div>
			<div class="col-md-4" style="float:right">
				<h4><a href="./logout">LOGOUT</a></h4>
			</div>
		</div>
	
		<div class="row"><!-- msg row -->
				<div class="col-md-12">
					<div style="color: red">${error}</div>
					<div style="color: green">${success}</div>
				</div>
		</div><!-- End of msg row -->
		<div class="row"><!-- main row -->
		
		<div class="col-md-4"><!-- form col -->
			<form action="${action}" method="post">
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
					<input type="hidden" name="pid" value="${idValue}"/>
	    				<label>NAME</label>
	    				<input type="text" class="form-control" name="name" value="${nameValue}">
	  				</div>
				</div>
			</div><!-- End of first row -->
			
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
	    				<label>AGE</label>
	    				<input type="text" class="form-control" name="age" value="${ageValue}">
	  				</div>
				</div>
			</div><!-- End of second row -->
			
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
	    				<label>PHONE</label>
	    				<input type="text" class="form-control" name="phone" value="${phoneValue}">
	  				</div>
				</div>
			</div><!-- End of third row -->	
			
			<div class="row">	
				<div class="col-md-12">
					<div class="form-group">
	    				<label>STATE</label>
	    				<select class="custom-select" name="state" id="state">
	    					<option value="Select">SELECT</option>
	        				<c:forEach items="${stateList}" var="state">
	        				<option value="${state.name}">${state.name}</option>
	        				</c:forEach>
	      				</select>
	  				</div>
				</div>
			</div><!-- End of forth row -->
			
			<div class="row">	
				<div class="col-md-12">
					<div class="form-group">
	    				<label>CITY</label>
	    				<select class="custom-select" name="city">
	    					<option selected disabled value="">SELECT CITY..</option>
	        				<option value="Nagpur" ${cityValue eq 'Nagpur' ? 'selected' : ''}>NAGPUR</option>
	        				<option value="Ahmedabad" ${cityValue eq  'Ahmedabad' ? 'selected' : ''}>AHMEDABAD</option>
	        				<option value="Mumbai" ${cityValue eq 'Mumbai' ? 'selected' : ''}>MUMBAI</option>
	        				<option value="Gandhinagar" ${cityValue eq 'Gandhinagar' ? 'selected' : ''}>GANDHINAGAR</option>
	      				</select>
	  				</div>
				</div>
			</div><!-- End of fifth row -->
			
			<div class="row">
				<div class="col-md-12" style="text-align: center;">
					<input type="submit" value="${button}" class="btn btn-primary"/>
				</div>
			</div>
			
		</form>
		</div><!-- end of form col -->
		
		<div class="col-md-8"><!-- details col -->
			<div class="row titlerow">
				<div class="col-md-12">
					<h2>PERSON DETAILS</h2>
				</div>
			</div>
			<div class="row headingrow">
				<div class="col-md-1">ID</div>
				<div class="col-md-2">NAME</div>
				<div class="col-md-1">AGE</div>
				<div class="col-md-2">PHONE</div>
				<div class="col-md-1">CITY</div>
				<div class="col-md-2">STATES</div>
				<div class="col-md-3">ACTION</div>
			</div>
			
			<c:forEach items="${personList}" var="person">
			<div class="row detailrow">
				<div class="col-md-1">${person.id}</div>
				<div class="col-md-2">${person.name}</div>
				<div class="col-md-1">${person.age}</div>
				<div class="col-md-2">${person.phone}</div>
				<div class="col-md-1">${person.city.name}</div>
				<div class="col-md-2">${person.city.state.name}</div>
				<div class="col-md-3">
				<a href="./getPersonEdit?id=${person.id}"><img alt="image" src="<c:url value="/res/images/b_edit.png"/>" /> EDIT</a>
				| 
				<a href="./getPersonDelete?id=${person.id}"><img alt="image" src="<c:url value="/res/images/b_drop.png"/>"/> DELETE</a>
				</div>
			</div>
			</c:forEach>
		</div><!-- end of details col -->
		</div><!-- end of main row -->
	</div>
</body>
</html>