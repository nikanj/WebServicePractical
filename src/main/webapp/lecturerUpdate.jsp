<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>

<html>
<body>
	<form action="lecturerUpdateController" method="post">
		Enter the new Lecture Name : <input type="text" name="newLectureName"> 
		Enter the Start Date (MM/DD/YYYY) : <input type="text"
			name="newLectureStartAt"> Enter the End Date (MM/DD/YYYY) : <input
			type="text" name="newLectureEndAt"> Make Lecture Rate able
		after (MM/DD/YYYY) : <input type="text" name="newLectureRateableAfter">
		<input type="submit" value="Update">
	</form>
</body>
</html>
