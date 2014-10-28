package controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
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

public class LecturerUpdateController extends HttpServlet {

	/**
	 * Get the new set of values from the lecturer and Update it in the 
	 * database using the LecturerClient object.
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
		//String newLectureName = req.getParameter("newLectureName");
		String newLectureStartAt = req.getParameter("newLectureStartAt");
		String newLectureEndAt = req.getParameter("newLectureEndAt");
		String newLectureRateableAfter = req.getParameter("newLectureRateableAfter");
		/*ApplicationContext context = new ClassPathXmlApplicationContext(
				"app-servlet.xml");

		LecturerClient lectureClientService = (LecturerClient) context
				.getBean("LecturerClient"); */ 
		LecturerClient lectureClientService = new LecturerClient();
		
		ServletContext sc = req.getSession().getServletContext();

		//String temp = Integer.toString((Integer) sc.getAttribute("lectureId")); 
		//Integer lectureId = Integer.parseInt(temp);
		int lectureId = Integer.parseInt(req.getParameter("lectureId"));
		try {
			Lecture lecture = (Lecture) sc.getAttribute("lectureObj_for_"+lectureId);
			//Lecture lecture = lectureClientService.getLecture(lectureId);
			//lecture.setName(newLectureName);

			//DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			
			Date LectureStartAt = df.parse(newLectureStartAt);
			Date LectureEndAt = df.parse(newLectureEndAt);
			Date LectureRateableAfter = df.parse(newLectureRateableAfter);

			System.out.println("lecture end: " + LectureEndAt + " " + lectureId);
			
			lecture.setStartAt(LectureStartAt.getTime());
			lecture.setEndAt(LectureEndAt.getTime());
			lecture.setRateableAfter(LectureRateableAfter.getTime());
			/**
			 * update lecture Object
			 */
			lectureClientService.updateLecture(lecture);
			
			sc.setAttribute("lectureObj_for_"+lectureId, lecture);
			
			//update question/answer lists
			List<Question> questionList = new ArrayList<Question>();
			HashMap<Integer, List<String>> questionAnswerMap = new HashMap<Integer, List<String>>();
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
			
			//set request values
			req.setAttribute("lectureId", lectureId);
			req.setAttribute("lectureName", lecture.getName());
			req.setAttribute("startAt", newLectureStartAt);
			req.setAttribute("endAt", newLectureEndAt);
			req.setAttribute("rateableAfter", newLectureRateableAfter); 
			
			req.setAttribute("questionList", questionList);
			req.setAttribute("questionAnswerMap", questionAnswerMap);
			
			long timeNow = new Date().getTime();
			if(timeNow >= LectureRateableAfter.getTime()) {
				req.setAttribute("rateable", true);
				
				Double rating = lectureClientService.getRating(lectureId);
				Double nan = new Double(Double.NaN);
				if(rating.equals(nan)) req.setAttribute("rating", "No rating was commited yet.");
				else req.setAttribute("rating", rating);
			}
			else {
				req.setAttribute("rateable", false);
			}
			
			RequestDispatcher rd = req.getRequestDispatcher("lecturerMainPage.jsp");
			rd.forward(req, resp);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownIdException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
