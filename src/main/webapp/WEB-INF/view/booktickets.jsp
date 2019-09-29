<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MBTA</title>
<style>
HTML  CSS  JS Result
EDIT ON
 * {
  box-sizing: border-box;
}
 
html,
body {
  font-family: 'Montserrat', sans-serif;
  font-size-adjust: u
  display: flex;
  width: 100%;
  height: 50%;
  background: #eeeeee;
  justify-content: center;
  align-items: center;
}
.checkout-panel {
  display: flex;
  flex-direction: column;
  width: 940px;
  height: 340px;
  background-color: rgb(255, 255, 255);
  box-shadow: 0 1px 1px 0 rgba(0, 0, 0, .2);
}
.panel-body {
  padding: 45px 80px 0;
  flex: 1;
}
 
.title {
  font-weight: 700;
  margin-top: 0;
  margin-bottom: 40px;
  color: #2e2e2e;
}
.progress-bar {
  display: flex;
  margin-bottom: 50px;
  justify-content: space-between;
}
 
.step {
  box-sizing: border-box;
  position: relative;
  z-index: 1;
  display: block;
  width: 25px;
  height: 25px;
  margin-bottom: 30px;
  border: 4px solid #fff;
  border-radius: 50%;
  background-color: #efefef;
}
 
.step:after {
  position: absolute;
  z-index: -1;
  top: 5px;
  left: 22px;
  width: 225px;
  height: 6px;
  content: '';
  background-color: #efefef;
}
 
.step:before {
  color: #2e2e2e;
  position: absolute;
  top: 40px;
}
 
.step:last-child:after {
  content: none;
}
 
.step.active {
  background-color: #1abc9c;
}
.step.active:after {
  background-color: #1abc9c;
}
.step.active:before {
  color: #1abc9c;
}
.step.active + .step {
  background-color: #1abc9c;
}
.step.active + .step:before {
  color: #1abc9c;
}
 
.step:nth-child(1):before {
  content: 'Cart';
}
.step:nth-child(2):before {
  right: -40px;
  content: 'Confirmation';
}
.step:nth-child(3):before {
  right: -30px;
  content: 'Payment';
}
.step:nth-child(4):before {
  right: 0;
  content: 'Finish';
}
.payment-method {
  display: flex;
  margin-bottom: 60px;
  justify-content: space-between;
}
 
.method {
  display: flex;
  flex-direction: column;
  width: 382px;
  height: 122px;
  padding-top: 20px;
  cursor: pointer;
  border: 1px solid transparent;
  border-radius: 2px;
  background-color: rgb(249, 249, 249);
  justify-content: center;
  align-items: center;
}
 
.card-logos {
  display: flex;
  width: 150px;
  justify-content: space-between;
  align-items: center;
}
 
.radio-input {
  margin-top: 20px;
}
 
input[type='radio'] {
  display: inline-block;
}
.input-fields {
  display: flex;
  justify-content: space-between;
}
 
.input-fields label {
  display: block;
  margin-bottom: 10px;
  color: #b4b4b4;
}
 
.info {
  font-size: 12px;
  font-weight: 300;
  display: block;
  margin-top: 50px;
  opacity: .5;
  color: #2e2e2e;
}
 
div[class*='column'] {
  width: 382px;
}
 
input[type='number'],
input[type='password'] {
  font-size: 16px;
  width: 100%;
  height: 50px;
  padding-right: 40px;
  padding-left: 16px;
  color: rgba(46, 46, 46, .8);
  border: 1px solid rgb(225, 225, 225);
  border-radius: 4px;
  outline: none;
}
 
input[type='number']:focus,
input[type='password']:focus {
  border-color: rgb(119, 219, 119);
}
 
#date { background: url(img/icons_calendar_black.png) no-repeat 95%; }
#cardholder { background: url(img/icons_person_black.png) no-repeat 95%; }
#cardnumber { background: url(img/icons_card_black.png) no-repeat 95%; }
#verification { background: url(img/icons_lock_black.png) no-repeat 95%; }
 
.small-inputs {
  display: flex;
  margin-top: 20px;
  justify-content: space-between;
}
 
.small-inputs div {
  width: 182px;
}
.panel-footer {
  display: flex;
  width: 100%;
  height: 96px;
  padding: 0 80px;
  background-color: rgb(239, 239, 239);
  justify-content: space-between;
  align-items: center;
}
.btn {
  font-size: 16px;
  width: 163px;
  height: 48px;
  cursor: pointer;
  transition: all .2s ease-in-out;
  letter-spacing: 1px;
  border: none;
  border-radius: 23px;
}
 
.back-btn {
  color: #1abc9c;
  background: #fff;
}
 
.next-btn {
  color: #fff;
  background: #1abc9c;
}
 
.btn:focus {
  outline: none;
}
 
.btn:hover {
  transform: scale(1.1);
}
.blue-border {
  border: 1px solid rgb(110, 178, 251);
}
.warning {
  border-color: #1abc9c;
}

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
.errorFaced {
color : red;
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
<br><br>
<table>
<tr>
<th>Train Name</th>
<th>Source Station</th>
<th>Destination Station</th>
<th>Seats Available</th>
</tr>
<tr>
<td><c:out value = "${train}"/></td>
<td><c:out value = "${origin}"/></td>
<td><c:out value = "${destination}"/></td>
<td><c:out value = "${availabeSeats.toString()}"/></td>
</tr>

</table>
<br><br>
<div class="checkout-panel">
  <div class="panel-body">
   
<form action="payment.htm" method="post">
	<label> Number of Passengers:</label>
	<input type="number" name="passengers" min="1" max="50" required/>
	<c:if test="${availError != null}">
		<div class="errorFaced">${availError}</div>
	</c:if>
	<br><br><label>Payment Method:</label><br><br>
	<div class="payment-method">
      <label for="card" class="method card">
        <div class="card-logos">
          <img src="https://designmodo.com/demo/checkout-panel/img/visa_logo.png"/>
          <img src="https://designmodo.com/demo/checkout-panel/img/mastercard_logo.png"/>
        </div>
 
        <div class="radio-input">
          <input id="card" type="radio" name="payment" value="card" required>
          Pay with credit card
        </div>
      </label>
 
      <label for="account" class="method paypal">
        <img src= "<c:url value="https://upload.wikimedia.org/wikipedia/commons/d/d5/Bank_account_-_The_Noun_Project.svg"/>" height="70" width="70"/>
        <div class="radio-input">
          <input id="account" type="radio" name="payment" value="account" >
          Pay with Bank Account
        </div>
      </label>
    </div>
    <div class="panel-footer">
	 <input class="btn next-btn" type="submit" value="Book" name="submit"/>
	 </div>
	 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</form>
</div></div>

</body>
</html>