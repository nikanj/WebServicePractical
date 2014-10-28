package controllers;

import java.io.IOException;
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
import Utilities.FinalPauseChart;
import Utilities.FinalSpeedChart;
import Utilities.QuestionInterval;
import Utilities.QuestionsPerInterval;
import de.tum.in.dss.project.Lecture;
import de.tum.in.dss.project.PauseVotingResult;
import de.tum.in.dss.project.Question;
import de.tum.in.dss.project.SpeedVotingResult;
import de.tum.in.dss.project.UnknownIdException;

public class LecturerController extends HttpServlet {
	/**
	 * This Controller gets all the data from the Database that is required to
	 * be displayed in the Lecturer Main Page
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	//@SuppressWarnings("unused")
	@SuppressWarnings("unused")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		ServletContext sc = req.getSession().getServletContext();
		//String lectureName = req.getParameter("lectureName");
		//int lectureId = (Integer) sc.getAttribute("lectureId");
		//Integer lectureId = Integer.parseInt(temp);
		
		int lectureId = Integer.parseInt(req.getParameter("lectureId"));
		
		try { 
			/**
			 * Get the lecture object associated with the lectureId
			 */
			Lecture lecture = (Lecture) sc.getAttribute("lectureObj_for_"+lectureId);
			
			LecturerClient lectureClientService = new LecturerClient();
//			Lecture lecture = null; 
//			lecture = lectureClientService.getLecture(lectureId);
//			Long start = lecture.getStartAt(); 
			
			/**
			 * This practically should have only one speed voting result entry
			 * in the List associated with the lecture.
			 */
//			List<SpeedVotingResult> speedVotingResult = new LinkedList<SpeedVotingResult>();
//			speedVotingResult = lectureClientService.getSpeedHistory(lectureId,
//					start); 
			/**
			 * This map contains the Speed Voting Result.
			 */
//			Map<Speed, Integer> speedVotingResultMap = new HashMap<Speed, Integer>();

			/**
			 * This practically should have only one pause voting result entry
			 * in the List associated with the lecture.
			 */
//			List<PauseVotingResult> pauseVotingResult = new LinkedList<PauseVotingResult>();
//			pauseVotingResult = lectureClientService.getPauseHistory(lectureId,
//					start); 
			/**
			 * Map contains the Pause Voting statistics
			 */
//			Map<Pause, Integer> pauseVotingResultMap = new HashMap<Pause, Integer>();
//			for (PauseVotingResult pvr : pauseVotingResult) {
//				pauseVotingResultMap = pvr.getEntries();
//			} 
			
			/**
			 * We make a list of questions associated with the lecture. We
			 * create a Hash Map of associated question Id with the list of
			 * answers associated from the database.
			 */
			List<Question> questionList = new ArrayList<Question>();
			HashMap<Integer, List<String>> questionAnswerMap = new HashMap<Integer, List<String>>();
			/*questionList = lectureClientService.getQuestions(lectureId); 
			for (Question tempQuestion : questionList) {
				List<String> tempAnswerList = new LinkedList<String>();
				for (int i = 0; i < tempQuestion.answers.size(); i++) {
					tempAnswerList.add(tempQuestion.answers.get(i));
				}
				questionAnswerMap.put(tempQuestion.id, tempAnswerList);
			}*/ 
			try {
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
			} catch (UnknownIdException e) {
				e.printStackTrace();
			} catch (TException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/**
			 * store all the values in the Servlet context for displaying it in
			 * the database.
			 */
//			sc.setAttribute("speedVotingResultMap", speedVotingResultMap);
//			sc.setAttribute("pauseVotingResultMap", pauseVotingResultMap);
			sc.setAttribute("lectureId", lectureId);
			//if(rating == 0.0) sc.setAttribute("rating", "No rating was commited yet.");
			//else sc.setAttribute("rating", rating);
//			sc.setAttribute("questionList", questionList);
//			sc.setAttribute("questionAnswerMap", questionAnswerMap);
			
			sc.setAttribute("questionList_for_"+lectureId, questionList);
			sc.setAttribute("answerMap_for_"+lectureId, questionAnswerMap);
			
			
			String lectureName = lecture.getName();
			//compute output values for the lectures dates
			Date startAtDate = new Date(lecture.getStartAt() );
			Date endAtDate = new Date(lecture.getEndAt() );
			Date rateableAfterDate = new Date(lecture.getRateableAfter() );
			String startAt = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(startAtDate);
			String endAt = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(endAtDate);
			String rateableAfter = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(rateableAfterDate);
			
			req.setAttribute("lectureId", lectureId);
			req.setAttribute("lectureName", lectureName);
			req.setAttribute("startAt", startAt);
			req.setAttribute("endAt", endAt);
			req.setAttribute("rateableAfter", rateableAfter);
			
			req.setAttribute("questionList", questionList);
			req.setAttribute("questionAnswerMap", questionAnswerMap);
			
			
			long timeNow = new Date().getTime();
			/**
			 * check if lecture has ended
			 */
			if(true) {
			//if(timeNow > lecture.getEndAt()) {
				/*FinalSpeedChart fsc = new FinalSpeedChart();
				List<SpeedVotingResult> svr = lectureClientService.getSpeedHistory(lectureId, lecture.getStartAt());
				fsc.createChart(svr);
				
				FinalPauseChart fpc = new FinalPauseChart();
				List<PauseVotingResult> pvr = lectureClientService.getPauseHistory(lectureId, lecture.getStartAt());
				fpc.createChart(pvr);
				*/
				QuestionsPerInterval qpi = new QuestionsPerInterval();
				qpi.lectureHasEnded(lectureId);
				List<QuestionInterval> intervalList = qpi.getQuestionsPerInterval();
				req.setAttribute("intervalList", intervalList);
				
				RequestDispatcher rd = req.getRequestDispatcher("lectureEnded.jsp");
				rd.forward(req, resp);
			}
			else {
				/**
				 * check if lecture is rateable yet and if true get and set the rating
				 */
				if(timeNow >= lecture.getRateableAfter()) {
					req.setAttribute("rateable", true);
					
					Double rating = lectureClientService.getRating(lectureId);
					System.out.println(rating);
					Double nan = new Double(Double.NaN);
					if(rating.equals(nan)) req.setAttribute("rating", "No rating was commited yet.");
					else req.setAttribute("rating", rating);
				}
				else {
					req.setAttribute("rateable", false);
				}
				
				RequestDispatcher rd = req.getRequestDispatcher("lecturerMainPage.jsp");
				rd.forward(req, resp);
			}
		} catch (UnknownIdException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (TException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
