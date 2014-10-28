<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>The lecture you followed has ended</title>
</head>
<body style="background-color:orange">
	<h2>The Lecture "<c:out value="${lectureName }"/>" has ended!</h2>
	<h3>Here are the statistics:</h3>
	<p>
	<b>Speed Voting Statistic:</b>
	<img src="speedChart.jpg" alt="TestDisplay"/>
	<p>
	<b>Pause Voting Statistic:</b>
	<img src="pauseChart.jpg" alt="TestDisplay"/>
	<p>
	<b>Questions per 15 minute interval:</b>
	<table border="1" align="center">
		<tr>
			<th>Interval Start:</td>
			<th>Interval End:</td>	
			<th>Questions Asked in this Interval:</td>	
		</tr>
		<c:forEach items="${intervalList}" var="interval">
			<tr>
				<td align="center"><c:out value="${interval.start}"/></td>
				<td align="center"><c:out value="${interval.end}"/></td>
				<td align="center"><c:out value="${interval.questions}"/></td>
			</tr>
		</c:forEach>
	</table>
	
	<h3>The questions that were asked (and all answers): </h3>
	<ul>
		<c:forEach items="${questionList}" var="entry">
			<li><c:out value="${entry.questionText}" /><c:out value=" (Voting: ${entry.voting})" />
				<ul>
					<c:forEach items="${entry.answers}" var="anslist" varStatus="status">
						<li><c:out value="${anslist}"></c:out></li>
					</c:forEach>
				</ul>
				<c:if test="${entry.isAnswered == false}">
					<i><c:out value="Question was not marked as answered."/></i>
				</c:if>
				<c:if test="${entry.isAnswered == true}">
					<i><c:out value="Question was marked as answered."/></i>
				</c:if>
			</li>
		</c:forEach>
	</ul>

</body>
</html>