<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/RegisterStyle.css"/>">
<link href="<c:url value='/resources/bootstrap/bootstrap.min.css' />"
	type="text/css" rel="stylesheet">
<script type="text/javascript"
	src="/resources/bootstrap/bootstrap.min.js"></script>
<title>Register Page</title>
</head>
<body background="<c:url value='/resources/images/back.jpg'/>">
	<div class="container">
		<h1 class="well">Registration Form</h1>
		<div class="col-lg-12 well">
			<div class="row">
				<form:form action="registerUser" method="post"
					commandName="userForm">
					<div class="col-sm-12">
						<div class="row">
							<div class="col-sm-6 form-group">
								<label>Username</label>
								<form:input path="username" type="text"
									placeholder="Enter Username Here.." class="form-control" />
							</div>
							<div class="col-sm-6 form-group">
								<label>Password</label>
								<form:input path="password" type="password"
									placeholder="Enter Password Here.." class="form-control" />
							</div>
						</div>

						<div class="form-group">
							<label>Email Address</label>
							<form:input path="email" type="text"
								placeholder="Enter Email Address Here.." class="form-control" />
						</div>
						<div class="form-group">
							<label>Age</label>
							<form:input path="age" type="number" min="0" max="110" step="1"
								value="18" class="form-control" />
						</div>
						<input type="submit" value="Submit" class="btn btn-lg btn-success" />
					</div>
				</form:form>
			</div>
		</div>
	</div>
	<center>
	<c:if test="${not empty errorRegisterMessage}">
		<div class='alert alert-warning' >
			<strong>Warning! </strong>${errorRegisterMessage}</div>
	</c:if>
	</center>
</body>
</html>