package controllers;

import java.io.IOException;
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

import thrift.StudentClient;
import de.tum.in.dss.project.Lecture;
import de.tum.in.dss.project.Pause;
import de.tum.in.dss.project.Question;
import de.tum.in.dss.project.Speed;
import de.tum.in.dss.project.UnknownIdException;

public class StudentController extends HttpServlet {
	/**
	 * To fetch all the Data the needed to be displayed in the Student main page.
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
		
		String lectureName = req.getParameter("lectureName");
		int lectureId = (Integer) sc.getAttribute("lectureId_for_"+lectureName);
		
		//int lectureId = (Integer) sc.getAttribute("lectureId");
		//int lectureId = Integer.parseInt(text);
		
		StudentClient studentClientService = new StudentClient();
		List<Question> questionList = new ArrayList<Question>();
		HashMap<Integer, List<String>> questionAnswerMap = new HashMap<Integer, List<String>>();
		try {
			questionList = studentClientService.getQuestions(lectureId);
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
			
			// add initial votings for speed and pause (default values Speed = OK, Pause = NO
			studentClientService.voteLectureSpeed(lectureId, Speed.OK, Speed.OK);
			studentClientService.voteForPause(lectureId, Pause.NO, Pause.NO);
			
		} catch (UnknownIdException e) {
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sc.setAttribute("questionList", questionList);
		sc.setAttribute("questionAnswerMap", questionAnswerMap);
		//sc.setAttribute("lectureId", lectureId);
		sc.setAttribute("questionList_for_"+lectureId, questionList);
		sc.setAttribute("answerMap_for_"+lectureId, questionAnswerMap);
		
		/*
		Object tempSpeed = null;
		tempSpeed = sc.getAttribute("oldSpeed");
		if(tempSpeed==null) {
			sc.setAttribute("oldSpeed", 2);
			req.setAttribute("oldSpeed", 2);
		}
		else req.setAttribute("oldSpeed", (Integer) sc.getAttribute("oldSpeed"));
		
		Object tempPause = null;
		tempPause = sc.getAttribute("oldPause");
		if(tempPause==null) {
			sc.setAttribute("oldPause", 2);
			req.setAttribute("oldPause", 2);
		}
		else req.setAttribute("oldPause", (Integer) sc.getAttribute("oldPause"));
		*/
		req.setAttribute("oldSpeed", 2);
		req.setAttribute("oldPause", 2);
		req.setAttribute("lectureId", lectureId);
		req.setAttribute("questionList", questionList);
		req.setAttribute("questionAnswerMap", questionAnswerMap);
		
		//check if lecture is rateable yet and set attribute accordingly
		//LecturerClient lecturerClient = new LecturerClient();
		//Lecture lecture = lecturerClient.getLecture(lectureId);
		Lecture lecture = (Lecture) sc.getAttribute("lectureObj_for_"+lectureId);
		long rateableAfter = lecture.getRateableAfter();
		long timeNow = new Date().getTime();
		if(timeNow >= rateableAfter) req.setAttribute("rateable", true);
		else req.setAttribute("rateable", false);
	
		
		RequestDispatcher rd = req.getRequestDispatcher("/studentMainPage.jsp");
		rd.forward(req, resp);
	}
}
