<%@page import="thrift.LecturerClient"%>
<%@page import="de.tum.in.dss.project.Question" %>
<%@page import="java.util.List" %>
<%@page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Student Main Page</title>
</head>
<body style="background-color:orange">
	<form action="studentReviewSpeedController" method="post">
		<h3>Speed :</h3>
		<div align="center">
			<!-- c:set var="test" value="${oldSpeed }" /-->
			<c:out value="${oldSpeed }"></c:out>
			<c:if test="${oldSpeed == 1}">
				<input type="radio" name="speed" value="1" checked="checked">SLOWER<br> 
				<input type="radio" name="speed" value="2">OK<br>
				<input type="radio" name="speed" value="3">FASTER<br>
			</c:if>
			<c:if test="${oldSpeed == 2}">
				<input type="radio" name="speed" value="1">SLOWER<br> 
				<input type="radio" name="speed" value="2" checked="checked">OK<br>
				<input type="radio" name="speed" value="3">FASTER<br>
			</c:if>
			<c:if test="${oldSpeed == 3}">
				<input type="radio" name="speed" value="1">SLOWER<br> 
				<input type="radio" name="speed" value="2">OK<br>
				<input type="radio" name="speed" value="3" checked="checked">FASTER<br>
			</c:if>
			<c:if test="${oldSpeed == null}">
				<input type="radio" name="speed" value="1">SLOWER<br> 
				<input type="radio" name="speed" value="2">OK<br>
				<input type="radio" name="speed" value="3">FASTER<br>
			</c:if>
			<input type="hidden" name="oldSpeed" value="${oldSpeed}">
			<input type="hidden" name="oldPause" value="${oldPause}">
			<input type="hidden" name="lectureId" value="${lectureId}">
			<input type="submit" name="Submit" value="Submit your voting for lecture speed"/>
		</div>
		</form>
		<hr>
		<form action="studentReviewPauseController" method="post">
		<h3>Pause :</h3>
		<div align="center">
			<c:out value="${oldPause}"></c:out>
			<c:if test="${oldPause == 1}">
				<input type="radio" name="pause" value="1" checked="checked">YES<br> 
				<input type="radio" name="pause" value="2">NO<br>
				<input type="radio" name="pause" value="3">SLEEPING<br>
			</c:if>
			<c:if test="${oldPause == 2}">
				<input type="radio" name="pause" value="1">YES<br> 
				<input type="radio" name="pause" value="2" checked="checked">NO<br>
				<input type="radio" name="pause" value="3">SLEEPING<br>
			</c:if>
			<c:if test="${oldPause == 3}">
				<input type="radio" name="pause" value="1">YES<br> 
				<input type="radio" name="pause" value="2">NO<br>
				<input type="radio" name="pause" value="3" checked="checked">SLEEPING<br>
			</c:if>
			<c:if test="${oldPause == null}">
				<input type="radio" name="pause" value="1">YES<br> 
				<input type="radio" name="pause" value="2">NO<br>
				<input type="radio" name="pause" value="3">SLEEPING<br>
			</c:if>
			<input type="hidden" name="oldPause" value="${oldPause}">
			<input type="hidden" name="oldSpeed" value="${oldSpeed}">
			<input type="hidden" name="lectureId" value="${lectureId}">
			<input type="submit" name="Submit" value="Submit your pause voting"/>
		</div>
		</form>
		<hr>
		<form action="studentReviewRatingController" method="post">
		<h3>Rating :</h3>
		<div align="center">
			<c:if test="${rateable == true}">
				<input type="radio" name="rating" value="1">VERY BAD<br>
				<input type="radio" name="rating" value="2">BAD<br> 
				<input type="radio" name="rating" value="3">OK<br>
				<input type="radio" name="rating" value="4">GOOD<br> 
				<input type="radio" name="rating" value="5">EXCELLENT<br>
				<input type="hidden" name="oldSpeed" value="${oldSpeed}">
				<input type="hidden" name="oldPause" value="${oldPause}">
				<input type="hidden" name="lectureId" value="${lectureId}">
				<input type="submit" name="Submit" value="Rate Lecture"/>
			</c:if>
			<c:if test="${rateable == false}">
				<c:out value="Rating the lecture is not yet possible!"/>
			</c:if>
		</div>
		<hr>
		
	</form>
	
	<form action="studentQuestionController" method="post">
		<div align="right">
			<input type ="text" name ="question"><br>
			<input type="hidden" name="oldSpeed" value="${oldSpeed}">
			<input type="hidden" name="oldPause" value="${oldPause}">
			<input type="hidden" name="lectureId" value="${lectureId}">
			<input type="submit" name="Ask Question" value="Ask Question">
		</div>
	</form>
	
	<h3>The questions that were asked for this lecture: </h3>
	<ul>
		<c:forEach items="${questionList}" var="entry">
			<li><c:out value="${entry.questionText}" /><c:out value=" (Voting: ${entry.voting})" />
				<form action="studentVote" method="post">
					<input type="hidden" name="questionId" value="${entry.id}">
					<input type="hidden" name="like" value="like">
					<input type="hidden" name="oldSpeed" value="${oldSpeed}">
					<input type="hidden" name="oldPause" value="${oldPause}">
					<input type="hidden" name="lectureId" value="${lectureId}">
					<!-- input type="submit" name="like" value="like"-->
					<input type="submit" name="vote" value="Like">
				</form>
				<ul>
					<c:forEach items="${entry.answers}" var="anslist" varStatus="status">
						<li><c:out value="${anslist}"></c:out></li>
					</c:forEach>
				</ul>
				<br>
				<c:if test="${entry.isAnswered == false}">
					<form action="studentAnswerController" method="Post">
						<input type="text" name="answer">
						<input type="hidden" name="questionId" value="${entry.id}">
						<input type="hidden" name="oldSpeed" value="${oldSpeed}">
						<input type="hidden" name="oldPause" value="${oldPause}">
						<input type="hidden" name="lectureId" value="${lectureId}">
						<input type="submit" name="Submit" value="Answer Question">
					</form>
				</c:if>
				<c:if test="${entry.isAnswered == true}">
					<c:out value="Question is marked as answered. No further answers may be commited."/>
				</c:if>
			</li>
		</c:forEach>
	</ul>
	
	<!-- table>
		<c:forEach items="${questionList}" var="entry">
			<tr>
				<td><c:out value="${entry.questionText}" /></td>
				<td><c:out value="Voting: ${entry.voting}" /></td>
				<td><ul>
           			<c:forEach items="${entry.answers}" var="anslist" varStatus="status">
           				<li><c:out value="${anslist}"></c:out></li>
           			</c:forEach>
           		</ul></td>
           		<td>
           			<form action="studentAnswerController" method="Post">
						<input type="text" name="answer">
						<input type="hidden" name="questionId" value="${entry.id}">
						<input type="hidden" name="oldSpeed" value="${oldSpeed}">
						<input type="hidden" name="oldPause" value="${oldPause}">
						<input type="submit" name="Submit" value="Answer Question">
					</form>
				</td>
				<td>
					<form action="studentVote" method="post">
						<input type="hidden" name="questionId" value=<c:out value="${entry.id}" /> >
						<input type="hidden" name="like" value="like">
						<input type="hidden" name="oldSpeed" value="${oldSpeed}">
						<input type="hidden" name="oldPause" value="${oldPause}">
						<input type="submit" name="vote" value="Like">
					</form>
				</td>
			</tr>
		</c:forEach>
	</table-->
</body>
</html>