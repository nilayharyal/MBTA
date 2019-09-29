<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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
</body>
</html>