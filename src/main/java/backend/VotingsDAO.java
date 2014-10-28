package backend;

import java.util.List;

import de.tum.in.dss.project.PauseVotingResult;
import de.tum.in.dss.project.SpeedVotingResult;
import de.tum.in.dss.project.UnknownIdException;

public interface VotingsDAO {

	public double getRating(int lectureID) throws UnknownIdException;
	
	public void voteLectureSpeed(int lectureID, int oldSpeed, int newSpeed) throws UnknownIdException;
	
	public void voteForPause(int lectureID, int oldPause, int newPause) throws UnknownIdException;
	
	public List<SpeedVotingResult> getSpeedHistory(int lectureID, long start) throws UnknownIdException;
	
	public List<PauseVotingResult> getPauseHistory(int lectureID, long start) throws UnknownIdException;
	
}
