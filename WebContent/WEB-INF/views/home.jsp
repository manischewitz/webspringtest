<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<html>
  <head>
    <title>MySpringMVC</title>
    <link rel="stylesheet"  type="text/css" href="<c:url value="/resources/style.css" />"/>
  </head>
  <body>
    <s:url value="/user/register" var="registerURL"/>
    <s:url value="/messages" var="parametrizedMessagesURL">
       <s:param name="max" value="60"/>
       <s:param name="count" value="333"/>
    </s:url>
    
    <h1><s:message code="home.welcome"/></h1>

     <a href="<c:url value="/messages" />">Messages</a> | 
     <a href="${registerURL}">Register</a>
     
     <p> <a href="${parametrizedMessagesURL}">Show 333 messages</a></p>
     <p><a href="<c:url value="/messagesfeed" />">Messages feed</a></p>
     <p><a href="<c:url value="/login" />">Login</a></p>
     
     <script>
     var url = "${parametrizedMessagesURL}";
     
     </script>
     
     
     
     <sec:authorize access="isAuthenticated()">
     <a href='<c:url value='/logout' />'>logOut</a></br>
     Hello <sec:authentication property="principal.username" />! </br>
     </sec:authorize>
     
     
    <sec:authorize access="hasAuthority('root')">
    <a href="<c:url value="/ebay" />">EBAY</a>
    </sec:authorize>
    
    <s:escapeBody htmlEscape="true">
     <h2>Escape content</h2>
     </s:escapeBody>
  </body>
</html> 
