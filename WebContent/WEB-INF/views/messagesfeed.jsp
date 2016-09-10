<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8" />
    <title>live feed</title>
    <script src="http://cdn.jsdelivr.net/sockjs/0.3.4/sockjs.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.js"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.js"></script>
    
    <link rel="stylesheet"  type="text/css" href="<c:url value="/resources/style.css" />"/>
</head>
<body>
		<p class="notifications">Notifications</p>	
		<p class="newMessages">New messages</p>	
			
		

	<sec:authorize access=" hasAnyAuthority('user','root') ">
    <f:form id = "messageForm" commandName="messageForm" enctype="multipart/form-data">
    <sec:authorize access="hasAuthority( 'root' )">
    <f:label path="username" cssErrorClass="errorLabel">Your username:</f:label></br>
    <f:input  name="username" type="text" class="fields" path="username" cssErrorClass="errorInput"/>
    <f:errors path="username" cssClass="error"/></br>
    </sec:authorize>
    
    
    <f:label path="message" cssErrorClass="errorLabel">Your message:</f:label></br>
    <f:textarea id="message" name="message"  type="text"  class="fields" path="message" cssErrorClass="errorInput"/></br>
    <f:errors path="message" cssClass="error"/></br>
    <f:textarea id="latitude" type="text"  class="fields" path="latitude" style="display:none;"/>
    <f:textarea id="longitude" type="text"  class="fields" path="longitude" style="display:none;"/>
    
   <input class="submit" type="submit" />
    </f:form>
   </sec:authorize>
   
   <c:forEach  items="${messagesList}" var="message">
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

    <script type="text/javascript">
        var url = "http://" + window.location.host + "/EEProject/stomp";
        var socket = new SockJS(url);
        var stomp = Stomp.over(socket);

        stomp.connect(
        	"guest",
            "guest",
            function (frame) {
                console.log("Connected.");
                stomp.subscribe("/topic/messagefeed", handleMessage);
                stomp.subscribe("/user/queue/notifications",handleNotification);
                stomp.subscribe("/user/queue/errors",handleException);
            });
        
        function handleException(incoming){
        	console.log("Exception is thrown.");
        	$(".notifications").prepend
        	("<b>ERROR: "+JSON.parse(incoming.body).message+"</b><br/>");
        }
        
        function handleNotification(incoming){
        	console.log("Notification "+incoming);
        	$(".notifications").prepend
        	("<b>Received: "+JSON.parse(incoming.body).notification+"</b><br/>");
        }
        
        function sendMessage(text){
        	
        	var username = $('#messageForm').find('input[name="username"]').val();
        	
        	if(text == null || text == undefined || text.length == 0 || text.length >777 || text == null){
        		$(".notifications").prepend
            	("<b>Message is too long or short.</b><br/>");
        		return;
        	}
        	console.log("Sending message: "+text);
        	stomp.send("/app/postmessage",{},
        	JSON.stringify({
        		"username":username,
        		"message":text,
        		"longitude":document.getElementById("latitude").textContent=Math.floor( Math.random()*90 ),
        		"latitude":document.getElementById("longitude").textContent=Math.floor( Math.random()*90 )}));
        
        }

        function handleMessage(incoming) {
            var message = JSON.parse(incoming.body);
            console.log('Received: ', message);
            $(".newMessages").prepend(message.message+" by "+message.username+"<br/>");
            
            
        }
        
        
        $('#messageForm').submit(submitForm);
        
        function submitForm(e){
        	e.preventDefault();
        	var message = $('#messageForm').find('textarea[name="message"]').val();
        	sendMessage(message);
        }
    </script>

    
    
    
     
    
    
    
    
    
    
    
    
    
    
    
    

</body>
</html>