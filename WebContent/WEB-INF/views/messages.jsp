<!DOCTYPE html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
  <head>
    <title>messages</title>
  </head>
  
  <body>
    <c:forEach items="${messagesList}" var="message">
    <li id="message"> 
       <div class="messageId">ID: <c:out value="${message.id}"/></div>
       <div class="Message"><c:out value="${message.message}"/></div>
       <div>
       <span class="messageTime"><c:out value="${message.time}"/></span>
       <span class="username"><c:out value="${message.username}"/></span>
       <span class ="messageLocation">(<c:out value="${message.latitude}"/>, <c:out value="${message.longitude}"/>)</span>
       </div>
    </li>
    </c:forEach>
    
   
    
    <sec:authorize access=" hasAnyAuthority('user','root') ">
    <f:form method="POST" commandName="messageForm" enctype="multipart/form-data">
    
    <sec:authorize access="hasAuthority( 'root' )">
    <f:label path="username" cssErrorClass="errorLabel">Your username:</f:label></br>
    <f:input  id="username" type="text" class="fields" path="username" cssErrorClass="errorInput"/>
    <f:errors path="username" cssClass="error"/></br>
    </sec:authorize>
    
    
    <f:label path="message" cssErrorClass="errorLabel">Your message:</f:label></br>
    <f:textarea  type="text"  class="fields" path="message" cssErrorClass="errorInput"/></br>
    <f:errors path="message" cssClass="error"/></br>
    <f:textarea id="latitude" type="text"  class="fields" path="latitude" style="display:none;"/>
    <f:textarea id="longitude" type="text"  class="fields" path="longitude" style="display:none;"/>
    
    
     <script type="text/javascript">
    document.getElementById("latitude").textContent=Math.floor( Math.random()*90 );
    document.getElementById("longitude").textContent=Math.floor( Math.random()*90 );
   // if(document.getElementById("username") == null) {document.getElementById("username").textContent="message"; alert();}
	</script>
	
     <input class="submit" type="submit" value="post"/>
    </f:form>
   </sec:authorize>
    
  </body>
</html>