package backend;

import de.tum.in.dss.project.Lecture;
import de.tum.in.dss.project.UnknownIdException;

public interface LecturesDAO {

	public int newLecture(String name);
	
	public Lecture getLecture(int id) throws UnknownIdException;
	
	public void rateLecture(int lectureID, int voting) throws UnknownIdException;
	
	public void updateLecture(Lecture lecture) throws UnknownIdException;
	
}
