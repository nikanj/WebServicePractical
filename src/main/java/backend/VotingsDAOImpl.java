package backend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import thrift.UpdateNotifier;
import de.tum.in.dss.project.Pause;
import de.tum.in.dss.project.PauseVotingResult;
import de.tum.in.dss.project.Speed;
import de.tum.in.dss.project.SpeedVotingResult;
import de.tum.in.dss.project.UnknownIdException;
import entities.PauseVoting;
import entities.SpeedVoting;

public class VotingsDAOImpl implements VotingsDAO {

	private SimpleJdbcTemplate template;

	@Autowired
	public void setDataSource(DriverManagerDataSource dataSource) {
		this.template = new SimpleJdbcTemplate(dataSource);
		//System.out.println("VotingsDAO: dataSource set!");
	}

	// RowMapper for int
	ParameterizedRowMapper<Integer> intMapping = new ParameterizedRowMapper<Integer>() {
		public Integer mapRow(ResultSet rs, int row) throws SQLException {
			return new Integer(rs.getInt(1));
		}
	};

	// return the average of all ratings for the lectureID
	public double getRating(int lectureID) throws UnknownIdException {
		//check if lecture with lectureID 'id' exists
		int check = template.queryForInt("SELECT lectureID FROM lectures WHERE lectureID = "+lectureID+"");
		if(check == 0) throw new UnknownIdException();
		
		List<Integer> votingList = template.query(
				"SELECT voting FROM evaluationvotes WHERE lectureID = "
						+ lectureID + "", intMapping);
		int length = votingList.size();

		double sum = 0.0;
		for (int i = 0; i < length; i++) {
			sum = sum + votingList.get(i);
		}
		double rating = sum / length;

		return rating;
	}

	ParameterizedRowMapper<SpeedVoting> speedVotingMapping = new ParameterizedRowMapper<SpeedVoting>() {
		public SpeedVoting mapRow(ResultSet rs, int row) throws SQLException {
			SpeedVoting speedVoting = new SpeedVoting();;
			speedVoting.setLectureID(rs.getInt(1));
			speedVoting.setTime(rs.getLong(2));
			speedVoting.setSlower(rs.getInt(3));
			speedVoting.setOk(rs.getInt(4));
			speedVoting.setFaster(rs.getInt(5));
			return speedVoting;
		}
	};
	
	// speed of the lecture
	public void voteLectureSpeed(int lectureID, int oldSpeed, int newSpeed)
			throws UnknownIdException {
		//check if lecture with lectureID 'id' exists
		int check = template.queryForInt("SELECT lectureID FROM lectures WHERE lectureID = "+lectureID+"");
		if(check == 0) throw new UnknownIdException();
		
		SpeedVoting recentVoting = new SpeedVoting();
		long timeNow = new Date().getTime();
		
		// get the most recent speed voting in the database for the given lectureID
		List<SpeedVoting> votingList = template.query("SELECT lectureID,time,slower,ok,faster FROM speedvotings" +
				" WHERE time = (SELECT max(time) FROM speedvotings WHERE lectureID = "+lectureID+")", speedVotingMapping);
		
		if(votingList.size() == 0) { //no speedvoting in table yet -> create a first one
			recentVoting.setLectureID(lectureID);
			recentVoting.setTime(timeNow);
			if(newSpeed == 1) {
				recentVoting.setSlower(1);
				recentVoting.setOk(0);
				recentVoting.setFaster(0);
			}
			else if(newSpeed == 2) {
				recentVoting.setSlower(0);
				recentVoting.setOk(1);
				recentVoting.setFaster(0);
			}
			else if(newSpeed == 3) {
				recentVoting.setSlower(0);
				recentVoting.setOk(0);
				recentVoting.setFaster(1);
			}
		}
		else {	// update recent voting with given values
			recentVoting = votingList.get(0);
			// apply change according to oldSpeed
			if(oldSpeed == 1) {
				if(recentVoting.getSlower() > 0) recentVoting.setSlower(recentVoting.getSlower() - 1);
			}
			else if(oldSpeed == 2) {
				if(recentVoting.getOk() > 0) recentVoting.setOk(recentVoting.getOk() - 1);
			}
			else {
				if(recentVoting.getFaster() > 0) recentVoting.setFaster(recentVoting.getFaster() - 1);
			}
			
			// apply change according to newSpeed
			if(newSpeed == 1) recentVoting.setSlower(recentVoting.getSlower() + 1);
			else if(newSpeed == 2) recentVoting.setOk(recentVoting.getOk() + 1);
			else recentVoting.setFaster(recentVoting.getFaster() + 1);
		}
		
		// make new entry in table speedvotings
		template.update("INSERT INTO speedvotings (lectureID,time,slower,ok,faster) values (?,?,?,?,?)", 
				new Object[] {lectureID, timeNow, recentVoting.getSlower(), recentVoting.getOk(), recentVoting.getFaster() } );
		
		// hand updateNotifier the newest SpeedVotingResult
		SpeedVotingResult svr = new SpeedVotingResult();
		svr.setTime(timeNow);
		Map<Speed,Integer> entries = new HashMap<Speed,Integer>();
		entries.put(Speed.findByValue(1), recentVoting.getSlower());
		entries.put(Speed.findByValue(2), recentVoting.getOk());
		entries.put(Speed.findByValue(3), recentVoting.getFaster());
		svr.setEntries(entries);
		try {
			UpdateNotifier un = new UpdateNotifier();
			un.changeToSpeedVoting(lectureID, svr);
		} catch (TException e) {
			System.out.println("TException in VotingsDAOImpl");
		}

	}

	
	ParameterizedRowMapper<PauseVoting> pauseVotingMapping = new ParameterizedRowMapper<PauseVoting>() {
		public PauseVoting mapRow(ResultSet rs, int row) throws SQLException {
			PauseVoting pauseVoting = new PauseVoting();;
			pauseVoting.setLectureID(rs.getInt(1));
			pauseVoting.setTime(rs.getLong(2));
			pauseVoting.setYes(rs.getInt(3));
			pauseVoting.setNo(rs.getInt(4));
			pauseVoting.setSleeping(rs.getInt(5));
			return pauseVoting;
		}
	};
	
	// 
	public void voteForPause(int lectureID, int oldPause, int newPause)
			throws UnknownIdException {
		//check if lecture with lectureID 'id' exists
		int check = template.queryForInt("SELECT lectureID FROM lectures WHERE lectureID = "+lectureID+"");
		if(check == 0) throw new UnknownIdException();
		
		PauseVoting recentVoting = new PauseVoting();
		long timeNow = new Date().getTime();
		
		// get the most recent speed voting in the database for the given lectureID
		List<PauseVoting> votingList = template.query("SELECT lectureID,time,yes,no,sleeping FROM pausevotings" +
				" WHERE time = (SELECT max(time) FROM pausevotings WHERE lectureID = "+lectureID+")", pauseVotingMapping);
		
		if(votingList.size() == 0) { //no pause voting in table yet -> create a first one
			recentVoting.setLectureID(lectureID);
			recentVoting.setTime(timeNow);
			if(newPause == 1) {
				recentVoting.setYes(1);
				recentVoting.setNo(0);
				recentVoting.setSleeping(0);
			}
			else if(newPause == 2) {
				recentVoting.setYes(0);
				recentVoting.setNo(1);
				recentVoting.setSleeping(0);
			}
			else {
				recentVoting.setYes(0);
				recentVoting.setNo(0);
				recentVoting.setSleeping(1);
			}
		}
		else { // update recent voting with given values
			recentVoting = votingList.get(0);
			recentVoting.setTime(timeNow);
			
			// apply change according to oldPause
			if(oldPause == 1) {
				if(recentVoting.getYes() > 0) recentVoting.setYes(recentVoting.getYes() - 1);
			}
			else if(oldPause == 2) {
				if(recentVoting.getNo() > 0) recentVoting.setNo(recentVoting.getNo() - 1);
			}
			else {
				if(recentVoting.getSleeping() > 0) recentVoting.setSleeping(recentVoting.getSleeping() - 1);
			}
			
			// apply change according to newSpeed
			if(newPause == 1) recentVoting.setYes(recentVoting.getYes() + 1);
			else if(newPause == 2) recentVoting.setNo(recentVoting.getNo() + 1);
			else recentVoting.setSleeping(recentVoting.getSleeping() + 1);
		}
		
		// make new entry in table pausevotings
		template.update("INSERT INTO pausevotings (lectureID,time,yes,no,sleeping) values (?,?,?,?,?)", 
				new Object[] {lectureID, timeNow, recentVoting.getYes(), recentVoting.getNo(), recentVoting.getSleeping() } );
		
		// hand updateNotifier the newest SpeedVotingResult
		PauseVotingResult pvr = new PauseVotingResult();
		pvr.setTime(timeNow);
		Map<Pause,Integer> entries = new HashMap<Pause,Integer>();
		entries.put(Pause.findByValue(1), recentVoting.getYes());
		entries.put(Pause.findByValue(2), recentVoting.getNo());
		entries.put(Pause.findByValue(3), recentVoting.getSleeping());
		pvr.setEntries(entries);
		try {
			UpdateNotifier un = new UpdateNotifier();
			un.changeToPauseVoting(lectureID, pvr);
		} catch (TException e) {
			System.out.println("TException in VotingsDAOImpl");
		}
	}

	/*
	 * FAST,1
	 */
	ParameterizedRowMapper<SpeedVotingResult> speedVoteMapping = new ParameterizedRowMapper<SpeedVotingResult>() {
		public SpeedVotingResult mapRow(ResultSet rs, int row)
				throws SQLException {
			SpeedVotingResult sVR = new SpeedVotingResult();
			Map<Speed, Integer> entries = new HashMap<Speed, Integer>();
			while (rs.next()) {
				sVR.setTime(rs.getInt(1));
				int speedVal = rs.getInt(2);
				Speed key = Speed.findByValue(speedVal);
				if (entries.containsKey(key)) {
					entries.put(key, entries.get(key) + 1);
				} else {
					entries.put(key, 1);
				}
			}
			sVR.setEntries(entries);
			return sVR;
		}
	};

	ParameterizedRowMapper<PauseVotingResult> pauseVoteMapping = new ParameterizedRowMapper<PauseVotingResult>() {
		public PauseVotingResult mapRow(ResultSet rs, int row)
				throws SQLException {
			PauseVotingResult pVR = new PauseVotingResult();
			pVR.setTime(rs.getInt(1));
			Map<Pause, Integer> entries = new HashMap<Pause, Integer>();
			while (rs.next()) {
				pVR.setTime(rs.getInt(1));
				int pauseVal = rs.getInt(2);
				Pause key = Pause.findByValue(pauseVal);
				if (entries.containsKey(key)) {
					entries.put(key, entries.get(key) + 1);
				} else {
					entries.put(key, 1);
				}
			}
			pVR.setEntries(entries);
			return pVR;
		}
	};

	// Get speed history
	public List<SpeedVotingResult> getSpeedHistory(int lectureID, long start)
			throws UnknownIdException {
		//check if lecture with lectureID 'id' exists
		int check = template.queryForInt("SELECT lectureID FROM lectures WHERE lectureID = "+lectureID+"");
		if(check == 0) throw new UnknownIdException();
		
		//get all speed votings for the lectureID from the speedvotings table
		List<SpeedVoting> votingsList = template.query("SELECT lectureID,time,slower,ok,faster FROM speedvotings" +
				" WHERE lectureID = "+lectureID+"", speedVotingMapping);
		
		List<SpeedVotingResult> resultList = new ArrayList<SpeedVotingResult>();
		for(int i=0; i<votingsList.size();i++) {
			SpeedVoting speedVoting = votingsList.get(i);
			
			SpeedVotingResult svr = new SpeedVotingResult();
			svr.setTime(speedVoting.getTime());
			Map<Speed,Integer> entries = new HashMap<Speed,Integer>();
			entries.put(Speed.findByValue(1), speedVoting.getSlower());
			entries.put(Speed.findByValue(2), speedVoting.getOk());
			entries.put(Speed.findByValue(3), speedVoting.getFaster());
			svr.setEntries(entries);
			resultList.add(svr);
		}
		
		/*
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String time = dateFormat.format(date);
		List<SpeedVotingResult> list = template
				.query("SELECT * FROM speedVotings WHERE lectureID = "
						+ lectureID + " AND ts =" + time + " ORDER BY ts",
						speedVoteMapping);
		*/
		return resultList;
	}

	// Get pause History
	public List<PauseVotingResult> getPauseHistory(int lectureID, long start)
			throws UnknownIdException {
		//check if lecture with lectureID 'id' exists
		int check = template.queryForInt("SELECT lectureID FROM lectures WHERE lectureID = "+lectureID+"");
		if(check == 0) throw new UnknownIdException();
		
		//get all speed votings for the lectureID from the speedvotings table
		List<PauseVoting> votingsList = template.query("SELECT lectureID,time,yes,no,sleeping FROM pausevotings" +
					" WHERE lectureID = "+lectureID+"", pauseVotingMapping);
				
		List<PauseVotingResult> resultList = new ArrayList<PauseVotingResult>();
		for(int i=0; i<votingsList.size();i++) {
			PauseVoting pauseVoting = votingsList.get(i);
					
			PauseVotingResult pvr = new PauseVotingResult();
			pvr.setTime(pauseVoting.getTime());
			Map<Pause,Integer> entries = new HashMap<Pause,Integer>();
			entries.put(Pause.findByValue(1), pauseVoting.getYes());
			entries.put(Pause.findByValue(2), pauseVoting.getNo());
			entries.put(Pause.findByValue(3), pauseVoting.getSleeping());
			pvr.setEntries(entries);
			resultList.add(pvr);
		}
		
		/*
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String time = dateFormat.format(date);
		List<PauseVotingResult> list = template
				.query("SELECT * FROM pauseVotings WHERE lectureID = "
						+ lectureID + " AND ts =" + time + " ORDER BY ts",
						pauseVoteMapping);
		*/
		return resultList;
	}

}
