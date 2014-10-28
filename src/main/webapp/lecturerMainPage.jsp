<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="./js/flot/jquery.min.js"></script>
<script src="./js/flot/excanvas.min.js"></script>
<script src="./js/flot/jquery.flot.min.js"></script>
<script type="text/javascript" src="./js/flot/jquery.flot.axislabels.js"></script>
<script type="text/javascript" src="./js/flot/jquery.flot.time.js"></script>
<script type="text/javascript" src="./js/flot/jquery.flot.pie.min.js"></script>
<style type="text/css">
#flotcontainer {
	width: 600px;
	height: 200px;
	text-align: center;
	margin: 0 auto;
}
</style>
<style type="text/css">
#flotcontainer2 {
	width: 600px;
	height: 200px;
	text-align: center;
	margin: 0 auto;
}
</style>


 
<script>
	var data = [];
	var data2 = [];
	var dataset;
	var totalPoints = 500;
	var updateInterval = 2000;
	var now = new Date().getTime();
	
	var x=0.0;
	var y=100.0;
	var z=0.0;
	
	var id = <%=(Integer)request.getAttribute("lectureId")%>;
	function GetData() {
		$.ajax({
			type : "POST",
			url : 'http://localhost:8080/groupproject/GraphDataController',
			dataType : "json",
			data : "id=" + id,
			success : onDataReceived
		});
	}
	function onDataReceived(recvData) {
		
		if (data.length > 10)
			data.shift();
		var avg = recvData.data[0];
		var temp = [ now += updateInterval, avg ];
		data.push(temp);
		
		 x = recvData.data[1];
		 
		 y = recvData.data[2];
		 z = recvData.data[3];
		 
	 data2 = [
		             {label: "Pause", data: x},
		             {label: "No Pause", data: y},
		     	{label: "Sleeping", data: z}

		         ]; 
	}
	
	
	
	var options = {
		series : {
			lines : {
				show : true,
				lineWidth : 1.2,
				fill : true
			}
		},
		xaxis : {
			mode : "time",
			tickSize : [ 2, "second" ],
			tickFormatter : function(v, axis) {
				var date = new Date(v);
				if (date.getSeconds() % 20 == 0) {
					var hours = date.getHours() < 10 ? "0" + date.getHours()
							: date.getHours();
					var minutes = date.getMinutes() < 10 ? "0"
							+ date.getMinutes() : date.getMinutes();
					var seconds = date.getSeconds() < 10 ? "0"
							+ date.getSeconds() : date.getSeconds();
					return hours + ":" + minutes + ":" + seconds;
				} else {
					return "";
				}
			},
			axisLabel : "Time",
			axisLabelUseCanvas : true,
			axisLabelFontSizePixels : 12,
			axisLabelFontFamily : 'Verdana, Arial',
			axisLabelPadding : 10
		},
		yaxis : {
			min : 0,
			max : 100,
			tickSize : 50,
			ticks : [ [ 0, "Slow" ], [ 50, "Ok" ], [ 100, "Faster" ] ],
			tickFormatter : function(v, axis) {
				if (v % 10 == 0) {
					return v + "%";
				} else {
					return "";
				}
			},
			axisLabel : "Speed",
			axisLabelUseCanvas : true,
			axisLabelFontSizePixels : 12,
			axisLabelFontFamily : 'Verdana, Arial',
			axisLabelPadding : 6
		},
		legend : {
			labelBoxBorderColor : "#fff"
		}
	};
	 var options2 = {
	            series: {
	                pie: {show: true}
	                    }
	         };
	$(document).ready(function() {
		GetData();
		
		dataset = [ {
			label : "Votes",
			data : data
		} ];

		 data2 = [
			             {label: "Pause", data: x},
			             {label: "No Pause", data: y},
			     	{label: "Sleeping", data: z}

			         ];
		
		$.plot($("#flotcontainer"), dataset, options);
		 $.plot($("#flotcontainer2"), data2, options2);
		function update() {
			GetData();
			$.plot($("#flotcontainer"), dataset, options); 
			
			
			 $.plot($("#flotcontainer2"), data2, options2);
			 setTimeout(update, updateInterval);
			 
		}
		update();
	});
</script>


</head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Lecturer Main Page</title>
</head>
<body>
	<b>You are logged in for the lecture '<c:out value="${lectureName}"/>'.</b>
	<form action="lecturerUpdateController" method="post">
		Lecture starts at:         		<input type="text" name="newLectureStartAt" value="${startAt}" size="17">
		<br>Lecture ends at:           	<input type="text" name="newLectureEndAt" value="${endAt}" size="17">
		<br>Lecture is rateable after: 	<input type="text" name="newLectureRateableAfter" value="${rateableAfter}" size="17">
		<input type="hidden" name="lectureId" value="${lectureId}">
		<br><input type="submit" value="Update Lecture Times">
	</form>
	<hr>
	<c:if test="${rateable == true}">
		Rating: ${rating}
	</c:if>
	<c:if test="${rateable == false }">
		Rating this lecture is not yet possible.
	</c:if>
	<br>
	<div id="flotcontainer"></div>
	<hr>
	<div>
		Hover percent : <span id="showInteractive"></span>
	</div>
	<div id="flotcontainer2"></div>
	
	
	<h3>The questions that were asked for this lecture: </h3>
	<ul>
		<c:forEach items="${questionList}" var="entry">
			<li><c:out value="${entry.questionText}" /><c:out value=" (Voting: ${entry.voting})" />
				<ul>
					<c:forEach items="${entry.answers}" var="anslist" varStatus="status">
						<li><c:out value="${anslist}"></c:out></li>
					</c:forEach>
				</ul>
				<c:if test="${entry.isAnswered == false}">
					<form action="lecturerMarkQuestionAsAnswered" method="post">
						<input type="hidden" name="questionId" value="${entry.id}">
						<input type="hidden" name="lectureId" value="${lectureId}">
						<input type="submit" name="markAnswered" value="Mark Question as Answered">
					</form>
				</c:if>
				<c:if test="${entry.isAnswered == true }">
					<c:out value="Marked as answered." />
				</c:if>
			</li>
		</c:forEach>
	</ul>
	
	
</body>
</html>
