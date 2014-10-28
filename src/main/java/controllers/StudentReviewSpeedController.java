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
import de.tum.in.dss.project.Speed;
import de.tum.in.dss.project.UnknownIdException;

public class StudentReviewSpeedController extends HttpServlet {
	/**
	 * the controller takes the student votes for speed associated with the Lecture
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
		
		int speedTemp = Integer.parseInt(req.getParameter("speed"));
		
		Speed speed = Speed.findByValue(speedTemp);
		
		try {
			Speed oldSpeed = Speed.findByValue(Integer.parseInt(req.getParameter("oldSpeed")));
			if(oldSpeed != speed) studentClient.voteLectureSpeed(lectureId, oldSpeed, speed);
			/*
			Object tempSpeed = null;
			
			tempSpeed = sc.getAttribute("oldSpeed");
			int speedOld;
			if (tempSpeed == null && loginCount == 1) {
				speedOld = 2;
			} else
				speedOld = (Integer) sc.getAttribute("oldSpeed");
			
			studentClient.voteLectureSpeed(lectureId, Speed.findByValue(speedOld), speed);
			
			sc.setAttribute("loginCount", (loginCount + 1));
			sc.setAttribute("oldSpeed", speed.getValue());
			*/
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
		
		req.setAttribute("oldSpeed", speed.getValue());
		req.setAttribute("oldPause", req.getParameter("oldPause") );
		req.setAttribute("lectureId", lectureId);
		
		/*
		Object tempPause = null;
		tempPause = sc.getAttribute("oldPause");
		if(tempPause==null) req.setAttribute("oldPause", 2);
		else req.setAttribute("oldPause", (Integer) sc.getAttribute("oldPause"));
		*/
		
		//check if lecture is rateable yet and set attribute accordingly
//		LecturerClient lecturerClient = new LecturerClient();
//		Lecture lecture = lecturerClient.getLecture(lectureId);
		Lecture lecture = (Lecture) sc.getAttribute("lectureObj_for_"+lectureId);		
		long rateableAfter = lecture.getRateableAfter();
		long timeNow = new Date().getTime();			
		if(timeNow >= rateableAfter) req.setAttribute("rateable", true);
		else req.setAttribute("rateable", false);
		
		List<Question> questionList = (List<Question>) sc.getAttribute("questionList_for_"+lectureId);
		HashMap<Integer, List<String>> questionAnswerMap = (HashMap<Integer, List<String>>) sc.getAttribute("answerMap_for_"+lectureId);
		req.setAttribute("questionList", questionList);
		req.setAttribute("questionAnswerMap", questionAnswerMap);
		
		RequestDispatcher rd = req.getRequestDispatcher("studentMainPage.jsp");
		rd.forward(req, resp);
	}
}
