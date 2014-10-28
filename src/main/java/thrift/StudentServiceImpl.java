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
import de.tum.in.dss.project.IllegalAnswerException;
import de.tum.in.dss.project.IllegalRatingException;
import de.tum.in.dss.project.Pause;
import de.tum.in.dss.project.Question;
import de.tum.in.dss.project.Rating;
import de.tum.in.dss.project.Speed;
import de.tum.in.dss.project.Student;
import de.tum.in.dss.project.UnknownIdException;

public class StudentServiceImpl implements Student.Iface {
	
	private UpdateNotifier updateNotifier;
	private LecturesDAO lecturesDAO;
	private QuestionsDAO questionsDAO;
	private VotingsDAO votingsDAO;
	
	@Autowired
	public void setUpdateNotifier(UpdateNotifier updateNotifier) {
		this.updateNotifier = updateNotifier;
		//System.out.println("StudentService: updateNotifier set!");
	}
	
	@Autowired
	public void setLecturesDAO(HbmLecturesDAOImpl hbmLecturesDAO) {
		this.lecturesDAO = hbmLecturesDAO;
		//System.out.println("StudentService: lecturesDAO set!");
	}
	
	@Autowired
	public void setQuestionsDAO(HbmQuestionsDAOImpl hbmQuestionsDAO) {
		this.questionsDAO = hbmQuestionsDAO;
		//System.out.println("StudentService: questionsDAO set!");
	}
	
	@Autowired
	public void setVotingsDAO(HbmVotingsDAOImpl hbmVotingsDAO) {
		this.votingsDAO = hbmVotingsDAO;
		//System.out.println("StudentService: votingsDAO set!");
	}
	
	
	@Override
	public void voteLectureSpeed(int lectureId, Speed speedOld, Speed speedNew)
			throws UnknownIdException, TException {
		int newSpeed = speedNew.getValue();
		int oldSpeed = speedOld.getValue();
		votingsDAO.voteLectureSpeed(lectureId, oldSpeed, newSpeed);
	}

	@Override
	public void voteForPause(int lectureId, Pause pauseOld, Pause pauseNew)
			throws UnknownIdException, TException {
		int oldPause = pauseOld.getValue();
		int newPause = pauseNew.getValue();
		votingsDAO.voteForPause(lectureId, oldPause, newPause);
	}

	@Override
	public List<Question> getQuestions(int lectureId)
			throws UnknownIdException, TException {
		return questionsDAO.getQuestions(lectureId);
	}

	@Override
	public void voteQuestion(int questionId, boolean vote)
			throws UnknownIdException, TException {
		questionsDAO.voteQuestion(questionId, vote);
	}

	@Override
	public Question addQuestion(int lectureId, String question)
			throws UnknownIdException, TException {
		Question newQuestion = questionsDAO.addQuestion(lectureId, question);
		return newQuestion;
	}

	@Override
	public void addAnswer(int questionId, String answer)
			throws UnknownIdException, IllegalAnswerException, TException {
		questionsDAO.addAnswer(questionId, answer);
	}

	@Override
	public void rateLecture(int lectureId, Rating rating)
			throws UnknownIdException, IllegalRatingException, TException {
		int voting = rating.getValue();
		if(voting < 1 || voting > 5) throw new IllegalRatingException();
		lecturesDAO.rateLecture(lectureId, voting); 
	}

	@Override
	public void registerCallback(String ip, int port) throws TException {
		updateNotifier.registerCallback(ip, port);
		
		System.out.println("Registered callback for ip: " + ip + " on port: " + port);
		System.out.println("# of callbacks registered: " + updateNotifier.getCallbacks().size() );
	}

}
