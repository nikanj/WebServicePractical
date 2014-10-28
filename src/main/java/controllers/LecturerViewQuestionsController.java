package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.tum.in.dss.project.Question;

public class LecturerViewQuestionsController extends HttpServlet {

	/**
	 * Fetch the Questions and limit it to the number mentioned by the Lecturer. 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String numberOfQuestions = req.getParameter("numberOfQuestions");
		ServletContext sc = req.getSession().getServletContext();

		List<Question> questionList = new ArrayList<Question>();
		HashMap<Integer, List<String>> questionAnswerMap = new HashMap<Integer, List<String>>();

		questionList = (List<Question>) sc.getAttribute("questionList");
		questionAnswerMap = (HashMap<Integer, List<String>>) sc
				.getAttribute("questionAnswerMap");

		int limit = Integer.parseInt(numberOfQuestions);
		limit = limit - 1;
		
		/**
		 * Limiting it the number mentioned by the Lecturer.
		 */
		questionList = questionList.subList(0, limit);

		List<Integer> limitedQuestionIdlist = new LinkedList<Integer>();

		for (int i = 0; i < questionList.size(); i++) {
			limitedQuestionIdlist.add(questionList.get(i).getId());
		}

		sc.setAttribute("limitedQuestions", questionList);
		sc.setAttribute("limitedQuestionIdlist", limitedQuestionIdlist);

		RequestDispatcher rd = req
				.getRequestDispatcher("lecturerViewQuestions.jsp");

		rd.forward(req, resp);

	}

}
