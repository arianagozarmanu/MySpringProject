<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page session="true"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="<c:url value='/resources/bootstrap/bootstrap.min.css' />"
	type="text/css" rel="stylesheet">
<script type="text/javascript"
	src="/resources/bootstrap/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/WelcomeStyle.css"/>">
<title>Home</title>
</head>
<body>

	<br />
	<br />
	<br />
	<br />
	<br />
	<center>
		<div id="header">
			<h1>Hello ${pageContext.request.userPrincipal.name}</h1>
			<span style="float: right; margin-right: 10%"><b>Your
					authorities: </b>${authorities}</span> <span
				style="float: left; margin-left: 10%"><b>Last Action
					Date: </b>${user.getLastOperationDate()}</span>
		</div>
		<br />
		<c:if test="${fn:contains(authorities,'Administrator')}">
			<form action="<c:url value='/welcome/addProduct'/>" method="get">
				<button class="btn btn-success" type="submit">Add Product</button>
			</form>
			<br />
			<c:if test="${not empty errorProductExists}">
				<div class='alert alert-warning'>
					<strong>Warning! </strong>${errorProductExists}</div>
			</c:if>
			<c:if test="${not empty successAddingProduct}">
				<div class='alert alert-success'>
					<strong>Success! </strong>${successAddingProduct}</div>
			</c:if>
			<c:if test="${not empty errorAddingProduct}">
				<div class='alert alert-danger'>${errorAddingProduct}</div>
			</c:if>
		</c:if>
		<br /> 
		<table>
			<tr>
				<th>#ID</th>
				<th>Name</th>
				<th>Price</th>
				<th>Action</th>
			</tr>
			<%
				int counter = 0;
			%>
			<c:forEach var="element" items="${products}">
				<tr>
					<%
						counter = counter + 1;
					%>
					<td align="center">${element.getIdproduct()}</td>
					<td align="left">${element.getName()}</td>
					<td align="right">${element.getPrice()}</td>

					<td align="left">
						<!-- <spring:url value="/welcome/delete/${element.getIdproduct()}" var="deleteUrl"/>
						<button class="btn btn-danger"
							onclick="location.href='${deleteUrl}'">Delete</button> -->

							<form style="display:inline-block;" action="welcome/delete?id=${element.getIdproduct()}"
								method="post">
								<input type="submit" value="Delete" class="btn btn-danger">
								<input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />
							</form>

						<span>
							<spring:url value="/welcome/update/${element.getIdproduct()}"
								var="updateUrl" />
							<button class="btn btn-primary"
								onclick="location.href='${updateUrl}'">Update</button>
						</span>
					</td>

				</tr>
			</c:forEach>
		</table>

		<br />
		<%
			String message = "You have " + counter + " rows in your table :)";
			out.write("<br/><font color=#A300E3>" + message + "</font>");
		%>
		<br /> <br /> <br />
		<div class="container">
			<img src="<c:url value="/resources/images/summer.jpg"/>" height="304"
				width="342" /> <img class="middle-img"
				src="<c:url value="/resources/images/hellosum2.jpg"/>" height="304"
				width="342" /> <img
				src="<c:url value="/resources/images/sumgirl.jpg"/>" height="304"
				width="342" />
		</div>

	</center>

	<br />


	<c:url value="/logout" var="logoutUrl" />

	<!-- csrt for log out-->
	<form id="logout" action="${logoutUrl}" method="post">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form>

	<c:if test="${pageContext.request.userPrincipal.name != null}">
		<div style="text-align: center;">
			<a href="javascript:document.getElementById('logout').submit()"><b>LOGOUT</b></a>
		</div>
	</c:if>

</body>
</html>