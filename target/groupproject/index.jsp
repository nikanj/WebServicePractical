<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>

<head>
	<title>Group Project Index Page</title>
</head>
<html>
<body style="background-color:orange">
	<center>
		<p style="color:blue;margin-left:3px;">LIVE FEEDBACK SYSTEM</p>
	</center>
	<div style="margin:0 auto" align=center>
		<h3>Login for Students to connect to a lecture:</h3>
		<form action="/groupproject/student.jsp" method="POST">
   	 		<input type="submit" value="Student Access">
		</form>
		<br>
		<br>
		<h3>Login for Lecturers (creates a new Lecture):</h3>
    	<form action="lecturerLoginController" method="POST">
    		<!-- Lecture Name : <input type="text" name= "lectureName"><br> -->
    		<input type="submit" value="Lecturer Access">
		</form>
	</div>
	<body style="background-color:orange">
</body>
</html>