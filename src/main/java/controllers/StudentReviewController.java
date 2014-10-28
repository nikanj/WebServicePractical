package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.thrift.TException;

import thrift.StudentClient;
import de.tum.in.dss.project.IllegalRatingException;
import de.tum.in.dss.project.Pause;
import de.tum.in.dss.project.Rating;
import de.tum.in.dss.project.Speed;
import de.tum.in.dss.project.UnknownIdException;

public class StudentReviewController extends HttpServlet {
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
		Integer loginCount = null;
		Object temp = sc.getAttribute("loginCount");
		if (temp == null) {
			loginCount = 1;
		} else
			loginCount = Integer.parseInt((String) temp);
		
		sc.setAttribute("loginCount", loginCount);
		
		Integer lectureId = (Integer) sc.getAttribute("lectureId");
		/*
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"app-servlet.xml");	
		StudentClient studentClient = (StudentClient) context
				.getBean("StudentClient");
		*/
		StudentClient studentClient = new StudentClient();
		//String speedTemp = req.getParameter("speed");
		//String pauseTemp = req.getParameter("pause");
		//String ratingTemp = req.getParameter("rating");
		
		int speedTemp = Integer.parseInt(req.getParameter("speed"));
		int pauseTemp = Integer.parseInt(req.getParameter("pause"));
		int ratingTemp = Integer.parseInt(req.getParameter("rating"));
		
		Speed speed = Speed.findByValue(speedTemp);
		Pause pause = Pause.findByValue(pauseTemp);
		Rating rating = Rating.findByValue(ratingTemp);
		
		String question = req.getParameter("askQuestion");
		if (!(question==null) && question.length() != 0 && !question.isEmpty() ) {
			try {
				studentClient.addQuestion(lectureId, question);
			} catch (UnknownIdException e) {
				e.printStackTrace();
			} catch (TException e) {
				e.printStackTrace();
			}
		}
		try {
			studentClient.rateLecture(lectureId, rating);
			Object temp1 = null, temp2 = null;
			temp1 = sc.getAttribute("oldPause");
			Pause pauseOld = null;
			if (temp1 == null && loginCount == 1) {
				pauseOld = Pause.NO;
			} else
				pauseOld = (Pause) sc.getAttribute("oldPause");
			temp2 = sc.getAttribute("oldSpeed");
			Speed speedOld = null;
			if (temp2 == null && loginCount == 1) {
				speedOld = Speed.OK;
			} else
				speedOld = (Speed) sc.getAttribute("oldSpeed");
			studentClient.voteForPause(lectureId, pauseOld, pause);
			studentClient.voteLectureSpeed(lectureId, speedOld, speed);
			studentClient.rateLecture(lectureId, rating);
			sc.setAttribute("loginCount", (loginCount + 1));
			sc.setAttribute("oldSpeed", speed);
			sc.setAttribute("oldPause", pause);
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
		RequestDispatcher rd = req.getRequestDispatcher("studentMainPage.jsp");
		rd.forward(req, resp);
	}
}
