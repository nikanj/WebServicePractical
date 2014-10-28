package controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.thrift.TException;

import thrift.LecturerClient;
import de.tum.in.dss.project.Lecture;
import de.tum.in.dss.project.Question;
import de.tum.in.dss.project.UnknownIdException;

public class LecturerMarkQuestionAsAnsweredController extends HttpServlet {

	/**
	 * to mark the question as answered for the Question ID
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		ServletContext sc = req.getSession().getServletContext();
		
		String questionId = req.getParameter("questionId");
		int lectureId = Integer.parseInt(req.getParameter("lectureId"));
		
		LecturerClient lectureClientService = new LecturerClient();
		
		Lecture lecture = (Lecture) sc.getAttribute("lectureObj_for_"+lectureId);
		
		int id = Integer.parseInt(questionId);
		List<Question> questionList = new ArrayList<Question>();
		HashMap<Integer, List<String>> questionAnswerMap = new HashMap<Integer, List<String>>();
		try {
			lectureClientService.markQuestionAsAnswered(id);
		
			questionList = lectureClientService.getQuestions(lectureId);
			if(questionList == null )
			{
				sc.setAttribute("message", "Incorrect Lecture Id");
				RequestDispatcher rd = req.getRequestDispatcher("/error.jsp");
				rd.forward(req, resp);
			}
			for (Question temp : questionList) {
				List<String> tempAnswerList = new LinkedList<String>();
				for (int i = 0; i < temp.answers.size(); i++) {
					tempAnswerList.add(temp.answers.get(i));
				}
				questionAnswerMap.put(temp.id, tempAnswerList);
			}
			
			sc.setAttribute("questionList_for_"+lectureId, questionList);
			sc.setAttribute("answerMap_for_"+lectureId, questionAnswerMap);
			
			long timeNow = new Date().getTime();
			if(timeNow >= lecture.getRateableAfter()) {
				req.setAttribute("rateable", true);
				
				Double rating = lectureClientService.getRating(lectureId);
				Double nan = new Double(Double.NaN);
				if(rating.equals(nan)) req.setAttribute("rating", "No rating was commited yet.");
				else req.setAttribute("rating", rating);
			}
			else {
				req.setAttribute("rateable", false);
			}
			
		} catch (UnknownIdException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//compute output values for the dates
		Date startAtDate = new Date(lecture.getStartAt() );
		Date endAtDate = new Date(lecture.getEndAt() );
		Date rateableAfterDate = new Date(lecture.getRateableAfter() );
		String startAt = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(startAtDate);
		String endAt = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(endAtDate);
		String rateableAfter = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(rateableAfterDate);
		
		//set attributes
		req.setAttribute("lectureId", lectureId);
		req.setAttribute("lectureName", lecture.getName());
		req.setAttribute("startAt", startAt);
		req.setAttribute("endAt", endAt);
		req.setAttribute("rateableAfter", rateableAfter);
		req.setAttribute("questionList", questionList);
		req.setAttribute("questionAnswerMap", questionAnswerMap);
		
		RequestDispatcher rd = req.getRequestDispatcher("lecturerMainPage.jsp");
		rd.forward(req, resp);
	}

}
