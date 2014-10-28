package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.thrift.TException;

import thrift.LecturerClient;
import de.tum.in.dss.project.Lecture;
import de.tum.in.dss.project.Pause;
import de.tum.in.dss.project.PauseVotingResult;
import de.tum.in.dss.project.Speed;
import de.tum.in.dss.project.SpeedVotingResult;
import de.tum.in.dss.project.UnknownIdException;

public class GraphDataController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		ServletContext sc = req.getSession().getServletContext();
		
		int lectureId = (Integer) sc.getAttribute("lectureId");
		LecturerClient lectureClientService = new LecturerClient();
		
		System.out.println("ID passed is: " + req.getParameter("id"));
		
		double resultSpeed = 2.0D;
		int yes = 0, no = 0, sleeping = 0;
		Lecture lecture = null;
		try {
			//lecture = lectureClientService.getLecture(lectureId);
			lecture = (Lecture) sc.getAttribute("lectureObj_for_"+lectureId);
			
			Long start = lecture.getStartAt();
			List<SpeedVotingResult> speedVotingResult = new LinkedList<SpeedVotingResult>();
			speedVotingResult = lectureClientService.getSpeedHistory(lectureId,start);
			/**
			 * This map contains the Speed Voting Result.
			 */
			Map<Speed, Integer> speedVotingResultMap = new HashMap<Speed, Integer>();
			if (speedVotingResult.size() > 0) {
				for(SpeedVotingResult svr : speedVotingResult)
					speedVotingResultMap = svr.getEntries();
					
					int slower = 0;
					int ok = 0;
					int faster = 0;
					
					for(Map.Entry<Speed,Integer> entry : speedVotingResultMap.entrySet()) {
						//System.out.println(entry.getKey() + " : " +entry.getValue());
						if(entry.getKey().equals(Speed.SLOWER)) slower = entry.getValue(); 
						if(entry.getKey().equals(Speed.OK)) ok = entry.getValue();
						if(entry.getKey().equals(Speed.FASTER)) faster = entry.getValue(); 
					}
					
					if(slower != 0 || ok != 0 || faster != 0) { // checks if a vote has been made yet
					
						double sumofvotes = slower + ok + faster;
						
						//slower = slower * 1;
						ok = ok * 2 ;
						faster = faster * 3;
						
						resultSpeed = (double)(slower+ok+faster) / sumofvotes;
					}
			}
			
			List<PauseVotingResult> pauseVotingResult = new LinkedList<PauseVotingResult>();
			pauseVotingResult = lectureClientService.getPauseHistory(
					lectureId, start);
			/**
			 * This map contains the Speed Voting Result.
			 */
			Map<Pause, Integer> pauseVotingResultMap = new HashMap<Pause, Integer>();
			if (pauseVotingResult.size() > 0) {
				for (PauseVotingResult svr : pauseVotingResult)
					pauseVotingResultMap = svr.getEntries();
				for (Map.Entry<Pause, Integer> entry : pauseVotingResultMap
						.entrySet()) {
					if (entry.getKey().equals(Pause.YES))
						yes = entry.getValue();
					if (entry.getKey().equals(Pause.NO))
						no = entry.getValue();
					if (entry.getKey().equals(Pause.SLEEPING))
						sleeping = entry.getValue();
				}
			}
			
		} catch (UnknownIdException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		double yesResult = 0.0, noResult=0.0, sleepingResult=0.0;
		if(yes == 0 && no == 0 && sleeping == 0) {
			noResult = 100.0;
		}
		else {
			double sum = yes + no + sleeping;
			//System.out.println("values: " + yes + " " +no+ " " + sleeping + " " +sum);
			yesResult = ( (double)yes / sum ) * 100.0; 
			noResult = ( (double)no / sum ) *  100.0;
			sleepingResult = ( (double)sleeping / sum ) * 100.0;
		}
		
		resultSpeed = ((resultSpeed-1) / 2) * 100;
		
		System.out.println("timestamp: " + new Date());
		System.out.println("speed: " + resultSpeed + " | pause: " + yesResult + "," +noResult+","+sleepingResult);
		
		String str = "{\"data\": [" + resultSpeed + "," + yesResult + "," + noResult
				+ "," + sleepingResult + "]}";
		resp.setContentType("application/json");
		PrintWriter out = resp.getWriter();
		out.println(str);
		out.flush();
	}
}
