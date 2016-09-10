<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>One Message</title>
  <link rel="stylesheet" 
          type="text/css" 
          href= "<c:url value="/resources/style.css" />" />
    <script>
      document.write("<h>JAva Script</h>");
    </script>
<!-- <style>
body {font-size:22px; height: 100px; width:500px;}
h1   {color:blue;}
p    {color:green;}
</style> -->
  </head>
  <body>
    <div class="message"><c:out value="${message.message}"/></div>
    <div>
    	<span class="messageTime"><c:out value="${message.time}"/></span>
    </div>
    <div><img src="https://yt3.ggpht.com/-XTlHEkpDLow/AAAAAAAAAAI/AAAAAAAAAAA/i9ys-EqUcfE/s88-c-k-no-rj-c0xffffff/photo.jpg"></div>
  </body>
</html>
