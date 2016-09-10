<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>login</title>
</head>
<body onload='document.f.username.focus();'>

	<h1>Login here</h1>

	<f:form method="POST" name="f" action='/EEProject/login'>
	
	
	<label>User Name:</label></br>
	<input type='text' name='username' value='' class="fields"></br>
	
	<label>Password:</label></br>
	<input type='text' name='password' class="fields"/></br>
	
	<input id="remember_me" name="remember-me" type="checkbox" />
    <label for="remember_me" class="fields">Remember me</label></br>
	
	<tr><td colspan='2'><input name="submit" type="submit" value="Login" class="fields"/></td></tr>
	</f:form>
</body>
</html>