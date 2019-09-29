<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<%@ page import="com.nilayharyal.dao.ScheduleDao"%>
<%@ page import="com.nilayharyal.entity.Schedule"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>

<!DOCTYPE html>
<html>
<head>
<link href="<c:url value="/resources/css/menu.css" />" rel="stylesheet">
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
	box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
	z-index: 1;
}

.dropdown-content a {
	color: black;
	padding: 12px 16px;
	text-decoration: none;
	display: block;
	text-align: left;
}

.dropdown-content a:hover {
	background-color: #f1f1f1;
}

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

	<div class="container">
		<form action="newbookings.htm" method="post">
			<br>
			<label for="travelDate">Travel Date</label> <br> <br>
			<input type="date" name="travelDate" required="required" /><br>
			<br>
			<label for="originStation">Origin Station</label> <br> <select
				name="originStation">
				<%
					List<String> stations = (List<String>) session.getAttribute("stations");
					if (stations != null)
						for (String a : stations) {
							pageContext.setAttribute("a", a);
				%>
				<option value="<c:out value="${a}"></c:out>"><c:out
						value="${a}"></c:out></option>
				<%
					}
				%>
			</select><br> <br>
			<label for="destinationStation">Destination Station</label> <br>
			<select name="destinationStation">
				<%
					if (stations != null)
						for (String a : stations) {
							pageContext.setAttribute("a", a);
				%>
				<option value="<c:out value="${a}"></c:out>"><c:out
						value="${a}"></c:out></option>
				<%
					}
				%>
			</select><br> <input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" /> <br>
			<input type="submit" value="Submit" name="register">
		</form>
	</div>
	<%
		if (request.getParameter("travelDate") != null) {
			ScheduleDao scheduleDao;
			List<Schedule> schedules = new ArrayList<Schedule>();
			String origin = request.getParameter("originStation");
			String destination = request.getParameter("destinationStation");
			schedules = (List) session.getAttribute("schedules");
			int count = 0;
	%>
	<br>
	<br>
	<table>
		<tr>
			<th>Train Name</th>
			<th>Departure Station</th>
			<th>Departure Time</th>
			<th>Arrival Station</th>
			<th>Arrival Time</th>
		</tr>

		<%
			for (Schedule schedule : schedules) {
					session.setAttribute("schedule", schedule);
					if (count % 2 == 0) {
		%>
		<tr>
			<td><a
				href="booktickets.htm?train=<c:out value="${schedule.getTrainName()}"/>"><c:out
						value="${schedule.getTrainName()}" /></a></td>
			<%
				}
						count = count + 1;
						if (schedule.getStation().equals(origin)) {
			%>
			<td><c:out value="${schedule.getStation()}" /></td>
			<td><c:out value="${schedule.getArrival()}" /></td>
			<%
				}
						if (schedule.getStation().equals(destination)) {
			%>
			<td><c:out value="${schedule.getStation()}" /></td>
			<td><c:out value="${schedule.getArrival()}" /></td>
			<%
				}
					}
			%>
		
	</table>
	<%
		}
	%>

	<script>
		var today = new Date().toISOString().split('T')[0];
		document.getElementsByName("travelDate")[0].setAttribute('min', today);
	</script>
</body>
</html>