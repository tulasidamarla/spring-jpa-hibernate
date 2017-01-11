<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Attendees Report page</title>
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
	<table>
		<thead><tr><th>ID</th><th>Name</th></tr></thead>
			<c:forEach items="${events}" var="event">
				<tr>
					<td>${event.id}</td>
					<td>${event.name}</td>
					<td>
						<table>
							<thead><tr><th>Name</th><th>Email</th>	<th>Phone</th></tr></thead>
							<c:forEach items="${event.attendees}" var="attendee">
								<tr><td>${attendee.name}</td><td>${attendee.email}</td><td>${attendee.phone}</td></tr>
							</c:forEach>
						</table>
					</td>
				</tr>
			</c:forEach>
	</table>
</body>
</html>