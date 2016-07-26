<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/CRUDPageStyle.css"/>">
<title>Update product</title>
<style>
</style>
<link href="<c:url value='/resources/bootstrap/bootstrap.min.css' />"
	type="text/css" rel="stylesheet">
<script type="text/javascript"
	src="/resources/bootstrap/bootstrap.min.js"></script>
</head>
<body background="<c:url value='/resources/images/back.jpg'/>">
	<div align="center" class="box">
		<form:form action="doUpdate" method="post" commandName="productForm">
			<form:input path="idproduct" value="${product.getIdproduct()}"
				type="hidden" />
			<form:input path="iduser" value="${product.getIduser()}"
				type="hidden" />
			<table border="0">
				<tr>
					<td colspan="2" align="center"><h2>Update Product ${product.getIdproduct()}:</h2></td>
				</tr>
				<tr>
					<td>Name:</td>
					<td><form:input path="name" value="${product.getName()}"
							style="margin-top:10px; margin-bottom:10px" /></td>
				</tr>
				<tr>
					<td>Price:</td>
					<td><form:input path="price" value="${product.getPrice()}" /></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><input type="submit"
						value="Update"
						style="background-color: #A83116; border-radius: 5px; color: white; padding: 10px 10px; margin-top: 20px" /></td>
				</tr>
			</table>
		</form:form>

	</div>

	<c:if test="${not empty invalidData}">
		<div class='alert alert-warning' align="center">
			<strong>Warning! </strong>${invalidData}</div>
	</c:if>

</body>
</html>