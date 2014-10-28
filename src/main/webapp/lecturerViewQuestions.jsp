<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>

<html>
<body>
		<c:forEach items="${limitedQuestions}" var="entry">
			
				Question ID: <c:out value="${entry.id}" /><br>
				Question : <c:out value="${entry.questionText}" /><br>
				
				<form action="lecturerMarkQuestionAsAnswered" method="post">
				 <input type="hidden" name="questionId" value="${entry.id}" >
				Mark as Answered : <input type="checkbox" name="marked" value="marked">
						<input type="submit" name="Mark">Marked</form>	
					
		</c:forEach>
		
		
		
	
	
	
	
	
	
	
	</body>
	</html>