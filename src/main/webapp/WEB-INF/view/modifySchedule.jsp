<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.List" %>
 <%@ page import="com.nilayharyal.entity.Schedule" %>
 <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
 <%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<style>
ul {
  list-style-type: none;
  margin: 0;
  padding: 0;
  overflow: hidden;
  background-color: #333;
}

li {
  float: left;
}

li a, .dropbtn {
  display: inline-block;
  color: white;
  text-align: center;
  padding: 14px 16px;
  text-decoration: none;
}

li a:hover, .dropdown:hover .dropbtn {
  background-color: blue;
}

li.dropdown {
  display: inline-block;
}

.dropdown-content {
  display: none;
  position: absolute;
  background-color: #f9f9f9;
  min-width: 160px;
  box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
  z-index: 1;
}

.dropdown-content a {
  color: black;
  padding: 12px 16px;
  text-decoration: none;
  display: block;
  text-align: left;
}

.dropdown-content a:hover {background-color: #f1f1f1;}

.dropdown:hover .dropdown-content {
  display: block;
}

table, th, td {
  border: 1px solid black;
}
</style>
</head>
<body>
<ul>
  <li><a href="home.htm">Home</a></li>
  <li><a href="schedule.htm">Schedule</a></li>
  <li><a href="newbookings.htm">New Bookings</a></li>
  <li><a href="mybookings.htm">Manage Bookings</a></li>
  
  <security:authorize access="hasRole('PASSENGER')">
  <li class="dropdown">
	<a href="javascript:void(0)" class="dropbtn">${user.firstName}</a>
     <div class="dropdown-content">
      <form:form action="${pageContext.request.contextPath}/logout" method="POST">
          <input class="dropdown-content" type="submit" value="Logout" />
       </form:form>
      </div>
      </li>
 </security:authorize>
 
  <security:authorize access="!{hasRole('PASSENGER') or hasRole('ADMIN')}">
     <li class="dropdown">
      <a href="javascript:void(0)" class="dropbtn">Sign In</a>
      <div class="dropdown-content">
      <a href="login.htm">Log In</a>
      <a href="${pageContext.request.contextPath}/register/showRegistrationForm">Register</a>
      </div>
      </li>
 </security:authorize>
 
 <security:authorize access="hasRole('ADMIN')">
  <li class="dropdown">
	<a href="javascript:void(0)" class="dropbtn">${user.firstName}</a>
     <div class="dropdown-content">
      <form:form action="${pageContext.request.contextPath}/logout" method="POST">
          <input class="dropdown-content" type="submit" value="Logout" />
       </form:form>
      </div>
      </li>
      
     <li class="dropdown">
	<a href="javascript:void(0)" class="dropbtn">Admin Access</a>
     <div class="dropdown-content">
      <a href="addSchedule.htm">Add Schedule</a> 
      <a href="modifySchedule.htm">Modify Schedule</a> 
      
      </div>
      </li>   
 </security:authorize>

  
</ul>
<br><br><br>
<form action="modifyschedule.htm" method="post">
<label>Train Name</label>
<select name="trainName">
<% 
List<String>trainname = (List<String>)session.getAttribute("trains"); 
if(trainname != null)
for(String a:trainname){
	pageContext.setAttribute("a",a);
%>
<option value="<c:out value="${a}"></c:out>"><c:out value="${a}"></c:out></option>
<% } %>
</select>
<input type="submit" name="submit" value="Load">
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</form>
<br><br>

	
	<table>
	<tr>
	<th>Station</th>
	<th>Arrival</th>
	<th>Delay</th>
	<th>Departure</th>
	</tr>
	<c:forEach items="${scheduleList}" var="schedule">
	<tr>
	<td>${schedule.station}</td>
	<td>${schedule.arrival}</td>
	<td>${schedule.delay}</td>
	<td>${schedule.departure}</td>
	<td><a href="updateschedule.htm?id=${schedule.trainId}">Update</a></td>
	<td><a href="deleteSchedule.htm?id=${schedule.trainId}">Delete</a></td>
	</tr>
	</c:forEach>
    </table>



</body>
</html>
