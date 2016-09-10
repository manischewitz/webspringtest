<!DOCTYPE html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>upload image</title>
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/style.css" />" >
</head>
<body>

	<h1>Picture:</h1>
	
	<f:form method="POST" enctype="multipart/form-data">
    
    <input class="submitUpload" type="file" name="picture" accept="image/jpeg,image/png,image/gif"/></br>
   
    <input class="submit" type="submit" value="Upload"/>
    </f:form>
	


</body>
</html>