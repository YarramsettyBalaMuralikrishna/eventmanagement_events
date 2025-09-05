<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h2>This is next page coming up!!</h2>
	<h4> Member Information</h4>
	<table>
		<tr>
			<td>Name:</td>
			<td>${user.name}</td>
		</tr>
		<tr>
			<td>Gender:</td>
			<td>${user.gender}</td>
		</tr>
		<tr>
			<td>City:</td>
			<td>${user.city}</td>
		</tr>
	</table>
</body>
</html>