package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.thrift.TException;

import thrift.StudentClient;
import de.tum.in.dss.project.IllegalRatingException;
import de.tum.in.dss.project.Lecture;
import de.tum.in.dss.project.Pause;
import de.tum.in.dss.project.Question;
import de.tum.in.dss.project.UnknownIdException;

public class StudentReviewPauseController extends HttpServlet {
	/**
	 * the controller takes the student votes for pause associated with the Lecture
	 */
	private static final long serialVersionUID = 1L;
	StudentClient client = new StudentClient();

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
			
		//Integer lectureId = (Integer) sc.getAttribute("lectureId");
		int lectureId = Integer.parseInt(req.getParameter("lectureId"));
		
		StudentClient studentClient = new StudentClient();
		
		int pauseTemp = Integer.parseInt(req.getParameter("pause"));
		
		Pause pause = Pause.findByValue(pauseTemp);
		
		try {
			Pause oldPause = Pause.findByValue(Integer.parseInt(req.getParameter("oldPause")));
			if(oldPause != pause) studentClient.voteForPause(lectureId, oldPause, pause);
		} catch (UnknownIdException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalRatingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		req.setAttribute("oldPause", pause.getValue());
		req.setAttribute("oldSpeed", req.getParameter("oldSpeed"));
		req.setAttribute("lectureId", lectureId);
		
		List<Question> questionList = (List<Question>) sc.getAttribute("questionList_for_"+lectureId);
		HashMap<Integer, List<String>> questionAnswerMap = (HashMap<Integer, List<String>>) sc.getAttribute("answerMap_for_"+lectureId);
		req.setAttribute("questionList", questionList);
		req.setAttribute("questionAnswerMap", questionAnswerMap);
		
//		LecturerClient lecturerClient = new LecturerClient();
//		Lecture lecture = lecturerClient.getLecture(lectureId);
		Lecture lecture = (Lecture) sc.getAttribute("lectureObj_for_"+lectureId);				
		long rateableAfter = lecture.getRateableAfter();
		long timeNow = new Date().getTime();				
		if(timeNow >= rateableAfter) req.setAttribute("rateable", true);
		else req.setAttribute("rateable", false);
		
		RequestDispatcher rd = req.getRequestDispatcher("studentMainPage.jsp");
		rd.forward(req, resp);
	}
}
