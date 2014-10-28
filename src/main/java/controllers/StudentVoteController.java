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
import de.tum.in.dss.project.Question;
import de.tum.in.dss.project.UnknownIdException;

public class StudentVoteController extends HttpServlet{

	/**
	 * To manage if the student has liked the question.
	 */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		ServletContext sc = req.getSession().getServletContext();
		String likeTemp = req.getParameter("like");
		String questionId = req.getParameter("questionId");
		//Integer lectureId = (Integer) sc.getAttribute("lectureId");
		int lectureId = Integer.parseInt(req.getParameter("lectureId"));
		
		boolean vote = false;
		if(likeTemp.equals("like")){
			vote = true;
		}
		
		/*
		Integer voteCount = null;
		Object temp3 = sc.getAttribute("voteCount");
		if(temp3 == null )
		{
			voteCount = 1;
		}
		else 
			voteCount = Integer.parseInt((String) temp3);
		
		if(voteCount == 1)
		{*/
			StudentClient studentClient = new StudentClient();
			try {
				// add voting
				studentClient.voteQuestion(Integer.parseInt(questionId), vote);
				
				//update the question&answer list in the servlet context
				List<Question> questionList = new ArrayList<Question>();
				HashMap<Integer, List<String>> questionAnswerMap = new HashMap<Integer, List<String>>();
				try {
					questionList = studentClient.getQuestions(lectureId);
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
				} catch (UnknownIdException e) {
					e.printStackTrace();
				} catch (TException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//sc.setAttribute("questionList", questionList);
				//sc.setAttribute("questionAnswerMap", questionAnswerMap);
				sc.setAttribute("questionList_for_"+lectureId, questionList);
				sc.setAttribute("answerMap_for_"+lectureId, questionAnswerMap);
				
				req.setAttribute("questionList", questionList);
				req.setAttribute("questionAnswerMap", questionAnswerMap);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnknownIdException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//}
		
		//sc.setAttribute("voteCount", (voteCount+1));
			
			//check if lecture is rateable yet and set attribute accordingly
//			LecturerClient lecturerClient = new LecturerClient();
//			Lecture lecture = lecturerClient.getLecture(lectureId);
			Lecture lecture = (Lecture) sc.getAttribute("lectureObj_for_"+lectureId);					
			long rateableAfter = lecture.getRateableAfter();
			long timeNow = new Date().getTime();						
			if(timeNow >= rateableAfter) req.setAttribute("rateable", true);
			else req.setAttribute("rateable", false);
		
			req.setAttribute("oldSpeed", req.getParameter("oldSpeed"));
			req.setAttribute("oldPause", req.getParameter("oldPause"));
			req.setAttribute("lectureId", lectureId);
			
			RequestDispatcher rd = req.getRequestDispatcher("studentMainPage.jsp");
			rd.forward(req, resp);
		
	}

}
