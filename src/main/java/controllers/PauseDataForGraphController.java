package controllers;

import java.io.IOException;
import java.io.PrintWriter;
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
import de.tum.in.dss.project.UnknownIdException;

public class PauseDataForGraphController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		int yes = 0, no = 0, sleeping = 0;

		ServletContext sc = req.getSession().getServletContext();
		int lectureId = (Integer) sc.getAttribute("lectureId");
		LecturerClient lectureClientService = new LecturerClient();
		Lecture lecture = null;
		try {
			lecture = lectureClientService.getLecture(lectureId);
			Long start = lecture.getStartAt();
			List<PauseVotingResult> pauseVotingResult = new LinkedList<PauseVotingResult>();
			pauseVotingResult = lectureClientService.getPauseHistory(lectureId,
					start);
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
			int sum = yes + no + sleeping;
			
			yesResult = ( yesResult / sum ) * 100;
			noResult = ( noResult / sum ) *  100;
			sleepingResult = ( sleepingResult / sum ) * 100;
		}
		

		String str = "{\"data\": [[" + yesResult + "],[" + noResult + "],[" + sleepingResult
				+ "]]}";
		resp.setContentType("application/json");
		PrintWriter out = resp.getWriter();
		out.println(str);
		out.flush();
	}

}
