<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h2>Welcome to the world of Spring!!</h2>
	<form action="save" method="post">
		<label for="name">Name: </label>
		<input type="text" id="name" name="name"/><br/>
		
		<label>Gender: </label>
		<input type="radio" name="gender" value="male"/>Male &nbsp;
		<input type="radio" name="gender" value="female"/>Female<br/>
		
		<label for="city">City: </label>
		<select id="city" name="city">
			<option value="pune">Pune</option>
			<option value="chennai">Chennai</option>
			<option value="hyderabd">Hyderabad</option>		
		</select>
		
		<br/><br/>
		<input type="submit"/>	
	</form>
</body>
</html>