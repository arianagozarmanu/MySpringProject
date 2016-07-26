<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login Page</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/LoginStyle.css"/>">

<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<link href="<c:url value='/resources/bootstrap/bootstrap.min.css' />"
	type="text/css" rel="stylesheet">
<script type="text/javascript"
	src="/resources/bootstrap/bootstrap.min.js"></script>
</head>

<body onload='document.loginForm.username.focus();'
	background="<c:url value='/resources/images/back.jpg'/>">
	<center>		
		<div class="login">
			<form action="<c:url value='/login' />"
				method="post">
				<br />
				<div style="text-align: left; margin-left: 30px">Username:</div>
				<input type="text" name="username" placeholder="Username"> <br />
				<br />
				<div style="text-align: left; margin-left: 30px">Password:</div>
				<input type="password" name="password" placeholder="Password">
				<div style="margin-top:10px">You don't have an account? Register <a href="<c:url value='/registration' />">here</a></div>
				<br /> <br /><input type="submit" value="Submit">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
			</form>
		</div>


		<br /> <br /> <br /> <br /> <br /> <br />

		<c:if test="${not empty error}">
			<div class='alert alert-warning'>
				<strong>Warning! </strong>${error}</div>
		</c:if>
		<c:if test="${not empty msg}">
			<div class='alert alert-success'>${msg}</div>
		</c:if>
		<c:if test="${not empty nopermis}">
			<div class='alert alert-danger'>${nopermis}</div>
		</c:if>
		<c:if test="${not empty successRegisterMessage}">
			<div class='alert alert-success'>
				<strong>Success! </strong>${successRegisterMessage}</div>
		</c:if>
		
	</center>

</body>
</html>