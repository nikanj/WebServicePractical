package thrift;

import java.util.List;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;

import backend.HbmLecturesDAOImpl;
import backend.HbmQuestionsDAOImpl;
import backend.HbmVotingsDAOImpl;
import backend.LecturesDAO;
import backend.QuestionsDAO;
import backend.VotingsDAO;
import de.tum.in.dss.project.Lecture;
import de.tum.in.dss.project.Lecturer;
import de.tum.in.dss.project.PauseVotingResult;
import de.tum.in.dss.project.Question;
import de.tum.in.dss.project.SpeedVotingResult;
import de.tum.in.dss.project.UnknownIdException;

public class LecturerServiceImpl implements Lecturer.Iface {
	
	private UpdateNotifier updateNotifier;
	private LecturesDAO lecturesDAO;
	private QuestionsDAO questionsDAO;
	private VotingsDAO votingsDAO;
	
	@Autowired
	public void setUpdateNotifier(UpdateNotifier updateNotifier) {
		this.updateNotifier = updateNotifier;
		//System.out.println("LecturerService: updateNotifier set!");
	}
	
	@Autowired
	public void setLecturesDAO(HbmLecturesDAOImpl hbmLecturesDAO) {
		this.lecturesDAO = hbmLecturesDAO;
		//System.out.println("LecturerService: lecturesDAO set!");
	}
	
	@Autowired
	public void setQuestionsDAO(HbmQuestionsDAOImpl hbmQuestionsDAO) {
		this.questionsDAO = hbmQuestionsDAO;
		//System.out.println("LecturerService: questionsDAO set!");
	}
	
	@Autowired
	public void setVotingsDAO(HbmVotingsDAOImpl hbmVotingsDAO) {
		this.votingsDAO = hbmVotingsDAO;
		//System.out.println("LecturerService: votingsDAO set!");
	}
	

	@Override
	public List<SpeedVotingResult> getSpeedHistory(int lectureId, long start)
			throws UnknownIdException, TException {
		List<SpeedVotingResult> resultList = votingsDAO.getSpeedHistory(lectureId, start);
		return resultList;
	}

	@Override
	public List<PauseVotingResult> getPauseHistory(int lectureId, long start)
			throws UnknownIdException, TException {
		List<PauseVotingResult> resultList = votingsDAO.getPauseHistory(lectureId, start);
		return resultList;
	}

	@Override
	public double getRating(int lectureId) throws UnknownIdException,
			TException {
		double rating = votingsDAO.getRating(lectureId);
		return rating;
	}

	@Override
	public List<Question> getQuestions(int lectureId)
			throws UnknownIdException, TException {
		List<Question> questions = questionsDAO.getQuestions(lectureId);
		return questions;
	}

	@Override
	public void markQuestionAsAnswered(int questionId)
			throws UnknownIdException, TException {
		questionsDAO.markQuestionAsAnswered(questionId);
	}

	@Override
	public int newLecture(String name) throws TException {
		int lectureId = lecturesDAO.newLecture(name);
		return lectureId;
	}

	@Override
	public Lecture getLecture(int id) throws UnknownIdException, TException {
		Lecture lecture = lecturesDAO.getLecture(id);
		return lecture;
	}

	@Override
	public void updateLecture(Lecture lecture) throws UnknownIdException,
			TException {
		lecturesDAO.updateLecture(lecture);
	}

	@Override
	public void registerCallback(String ip, int port) throws TException {
		updateNotifier.registerCallback(ip, port);
		
		System.out.println("Registered callback for ip: " + ip + " on port: " + port);
		System.out.println("# of callbacks registered: " + updateNotifier.getCallbacks().size() );
	}

}
