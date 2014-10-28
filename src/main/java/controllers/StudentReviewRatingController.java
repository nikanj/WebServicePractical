package controllers;

import java.io.IOException;
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
import de.tum.in.dss.project.Question;
import de.tum.in.dss.project.Rating;
import de.tum.in.dss.project.UnknownIdException;

public class StudentReviewRatingController extends HttpServlet {
	/**
	 * the controller takes the student votes associated with the Lecture
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
		/**
		 * loginCount is used to keep track of the previous Selection by the 
		 * student
		 */
		/*
		Integer loginCount = null;
		Object temp = sc.getAttribute("loginCount");
		if (temp == null) {
			loginCount = 1;
		} else
			//loginCount = Integer.parseInt((String) temp);
			loginCount = (Integer) temp;
		*/	
			
		//Integer lectureId = (Integer) sc.getAttribute("lectureId");
		int lectureId = Integer.parseInt(req.getParameter("lectureId"));
		
		StudentClient studentClient = new StudentClient();
		
		int ratingTemp = Integer.parseInt(req.getParameter("rating"));
		
		Rating rating = Rating.findByValue(ratingTemp);
		
		try {
			studentClient.rateLecture(lectureId, rating);
			
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
		
		//check if lecture is rateable yet and set attribute accordingly
//		LecturerClient lecturerClient = new LecturerClient();
//		Lecture lecture = lecturerClient.getLecture(lectureId);
		Lecture lecture = (Lecture) sc.getAttribute("lectureObj_for_"+lectureId);							
		long rateableAfter = lecture.getRateableAfter();
		long timeNow = new Date().getTime();									
		if(timeNow >= rateableAfter) req.setAttribute("rateable", true);
		else req.setAttribute("rateable", false);
		
		req.setAttribute("oldSpeed", req.getParameter("oldSpeed"));
		req.setAttribute("oldPause", req.getParameter("oldPause"));
		req.setAttribute("lectureId", lectureId);
		
		List<Question> questionList = (List<Question>) sc.getAttribute("questionList_for_"+lectureId);
		HashMap<Integer, List<String>> questionAnswerMap = (HashMap<Integer, List<String>>) sc.getAttribute("answerMap_for_"+lectureId);
		req.setAttribute("questionList", questionList);
		req.setAttribute("questionAnswerMap", questionAnswerMap);
		
		RequestDispatcher rd = req.getRequestDispatcher("studentMainPage.jsp");
		rd.forward(req, resp);
	}
}


