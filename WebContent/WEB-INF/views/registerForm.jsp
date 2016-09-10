<!DOCTYPE html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
  <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  
    <title>register</title>
    <link rel="stylesheet" type="text/css" 
          href="<c:url value="/resources/style.css" />" >
  </head>
  <body>
    <h1>Register</h1>

	<!--  OLD  --
    <form method="POST">
      First Name: <input type="text" name="firstName" /><br/>
      Last Name: <input type="text" name="lastName" /><br/>
      Email: <input type="email" name="email" /><br/>
      Username: <input type="text" name="username" /><br/>
      Password: <input type="password" name="password" /><br/>
      <input type="submit" value="Register" />
    </form> 
    -->
    
    <f:form method="POST" commandName="user" enctype="multipart/form-data">
    
    <f:label path="firstName" cssErrorClass="errorLabel">First Name:</f:label>
    <f:input class="fields" path="firstName" cssErrorClass="errorInput"/>
    <f:errors path="firstName" cssClass="error"/></br>
    
    Last Name: <f:input class="fields" path="lastName"/></br>
    Email: <f:input class="fields" path="email"/></br>
    Username: <f:input class="fields" path="username"/></br>
    
    <f:label path="password" cssErrorClass="errorLabel">Password:</f:label>
    <f:input type="password" class="fields" path="password" cssErrorClass="errorInput"/>
    <f:errors path="password" cssClass="error"/></br>
    
    <f:label path="id">ID:</f:label>
    <f:input type="text" class="fields" path="id"/></br>
    
    
   
    <input class="submit" type="submit" value="Register"/>
    </f:form>
    
    
  </body>
</html>