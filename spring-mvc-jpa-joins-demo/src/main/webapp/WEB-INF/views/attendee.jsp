<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Attendee page</title>
<style type="text/css">
	.error{
		color:#ff0000;
	}
	.errorblock{
		color: red;
		background-color: : #ffEEEE;
		border : 3px solid red;
		padding: 8px;
		margin: 16px;
	}
</style>
</head>
<body>
	<a href="?language=en">English</a>
	<a href="?language=es">Spanish</a>
	
	<form:form commandName="attendee">
	 	<form:errors path="*" cssClass="errorblock" element="div"></form:errors>
	 	<label for="textinput1"> <spring:message code="attendee.name"/></label>
	 	<form:input path="name" cssErrorClass="error"/>
	 	<form:errors path="name" cssClass="error"/>
	 	<br>
	 	
	 	<label for="textinput2"> <spring:message code="attendee.email"/></label>
	 	<form:input path="email" cssErrorClass="error"/>
	 	<form:errors path="email" cssClass="error"/>
	 	<br>
	 	
	 	<label for="textinput3"> <spring:message code="attendee.phone"/></label>
	 	<form:input path="phone" cssErrorClass="error"/>
	 	<form:errors path="phone" cssClass="error"/>
	 	<br>
	 	
	 	<input type="submit" class="btn" value="Enter Attendee"/>
	</form:form>
</body>
</html>