package controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.thrift.TException;
import org.eclipse.jetty.util.ajax.JSON;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONML;
import org.json.JSONObject;

import Utilities.AverageCalculator;

import thrift.LecturerClient;
import de.tum.in.dss.project.Lecture;
import de.tum.in.dss.project.Speed;
import de.tum.in.dss.project.SpeedVotingResult;
import de.tum.in.dss.project.UnknownIdException;

public class UpdateLecturerController extends HttpServlet {
	/**
	 * This Controller gets all the data from the Database that is required to
	 * be displayed in the Lecturer Main Page
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		ServletContext sc = req.getSession().getServletContext();
		String lectureName = req.getParameter("lectureName");
		int lectureId = (Integer) sc.getAttribute("lectureId");
		// Integer lectureId = Integer.parseInt(temp);

		try {
			/**
			 * We get a lecture Object associated with the Lecture Id
			 */
			/*
			ApplicationContext context = new ClassPathXmlApplicationContext(
					"app-servlet.xml");
			LecturerClient lectureClientService = (LecturerClient) context
					.getBean("LecturerClient");
			*/
			LecturerClient lectureClientService = new LecturerClient();
			
			Lecture lecture = null;
			lecture = lectureClientService.getLecture(lectureId);
			Long start = lecture.getStartAt();
			/**
			 * This practically should have only one speed voting result entry
			 * in the List associated with the lecture.
			 */
			List<SpeedVotingResult> speedVotingResult = new LinkedList<SpeedVotingResult>();
			speedVotingResult = lectureClientService.getSpeedHistory(lectureId,
					start);
			/**
			 * This map contains the Speed Voting Result.
			 */
			Map<Speed, Integer> speedVotingResultMap = new HashMap<Speed, Integer>();

			double default_average = 2.0;
			
			JSONObject jsonResultSpeed = new JSONObject();
			if(speedVotingResult.size() > 0) {
				SpeedVotingResult svrX = speedVotingResult.get(0);
				Map<Speed,Integer> entries = svrX.getEntries(); 
				
				int slower = 0;
				int ok = 0;
				int faster = 0;
				
				System.out.println("At time "+svrX.getTime()+" : ");
				for(Map.Entry<Speed,Integer> entry : entries.entrySet()) {
					System.out.println(entry.getKey() + " : " +entry.getValue());
					if(entry.getKey().equals(Speed.SLOWER)) slower = entry.getValue();
					if(entry.getKey().equals(Speed.OK)) ok = entry.getValue();
					if(entry.getKey().equals(Speed.FASTER)) entry.getValue();
				}
				
				int sumofvotes = slower + ok + faster;
				
				slower = slower * 1;
				ok = ok * 2 ;
				faster = faster * 3;
				
				
				double resultSpeed = (slower+ok+faster) / sumofvotes;
				
				jsonResultSpeed = new JSONObject(resultSpeed);
				
			} 
			else {
				jsonResultSpeed = new JSONObject(default_average);
			}
			//JSONArray jsonResultArr = new JSONArray();
			//jsonResultArr.put(jsonResultSpeed);
			
			/*
			JSONArray jsonMapArr = new JSONArray();
			for (SpeedVotingResult svr : speedVotingResult) {
				speedVotingResultMap = svr.getEntries();
				System.out.println(svr.time);
				JSONObject jsonMap = new JSONObject();
				Iterator it = speedVotingResultMap.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry pairs = (Map.Entry) it.next();
					System.out.println(pairs.getKey() + " = "
							+ pairs.getValue());
					jsonMap.append(pairs.getKey().toString(), pairs.getValue());
				}
				jsonMapArr.put(jsonMap);
			}
			*/
			sc.setAttribute("speedVotingResultMap", speedVotingResultMap);
			req.setAttribute("resultSpeed", jsonResultSpeed.toString());
			RequestDispatcher rd = req
					.getRequestDispatcher("lecturerMainPage.jsp");

			rd.forward(req, resp);
		} catch (UnknownIdException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (TException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} /*catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */
	}
}
