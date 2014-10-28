<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ page import="org.springframework.web.bind.ServletRequestUtils" %>
	<%@ page import="javax.servlet.ServletContext" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Student Answer Page</title>
</head>
<body>
<form action="studentAnswerController" method="Post">
Please write your answer here : <input type="text" name="answer">
<%
String tempQuestionId = ServletRequestUtils.getStringParameter(request, "id");
ServletContext sc = request.getSession().getServletContext();
sc.setAttribute("questionId", tempQuestionId);
%>
<input type = "submit" name = "Submit">
</form>

</body>
</html>