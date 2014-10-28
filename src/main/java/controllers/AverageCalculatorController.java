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
import de.tum.in.dss.project.Speed;
import de.tum.in.dss.project.SpeedVotingResult;
import de.tum.in.dss.project.UnknownIdException;

public class AverageCalculatorController extends HttpServlet {

	private static final long serialVersionUID = -8221316751778266269L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		ServletContext sc = req.getSession().getServletContext();
		int lectureId = (Integer) sc.getAttribute("lectureId");
		LecturerClient lectureClientService = new LecturerClient();
		double resultSpeed = 2.0D;
		Lecture lecture = null;
		try {
			lecture = lectureClientService.getLecture(lectureId);
			Long start = lecture.getStartAt();
			List<SpeedVotingResult> speedVotingResult = new LinkedList<SpeedVotingResult>();

			speedVotingResult = lectureClientService.getSpeedHistory(lectureId,
					start);
			/**
			 * This map contains the Speed Voting Result.
			 */
			Map<Speed, Integer> speedVotingResultMap = new HashMap<Speed, Integer>();

			if (speedVotingResult.size() > 0) {
				for (SpeedVotingResult svr : speedVotingResult)
					speedVotingResultMap = svr.getEntries();

				int slower = 0;
				int ok = 0;
				int faster = 0;

				for (Map.Entry<Speed, Integer> entry : speedVotingResultMap
						.entrySet()) {
					System.out.println(entry.getKey() + " : "
							+ entry.getValue());
					if (entry.getKey().equals(Speed.SLOWER))
						slower = entry.getValue();
					if (entry.getKey().equals(Speed.OK))
						ok = entry.getValue();
					if (entry.getKey().equals(Speed.FASTER))
						faster = entry.getValue();
				}

				if (slower != 0 || ok != 0 || faster != 0) { // checks if a vote
																// has been made
																// yet

					int sumofvotes = slower + ok + faster;

					// slower = slower * 1;
					ok = ok * 2;
					faster = faster * 3;

					resultSpeed = (slower + ok + faster) / sumofvotes;
				}

			}
		} catch (UnknownIdException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		resultSpeed = Math.random() * 100;

		System.out.println("********************** " + resultSpeed + " | "
				+ new Date());

		String str = "{\"data\": [" + resultSpeed + "]}";
		resp.setContentType("application/json");
		PrintWriter out = resp.getWriter();
		out.println(str);
		out.flush();
	}

}
