package backend;

import java.util.List;

import de.tum.in.dss.project.IllegalAnswerException;
import de.tum.in.dss.project.Question;
import de.tum.in.dss.project.UnknownIdException;

public interface QuestionsDAO {

	public List<Question> getQuestions(int lectureID) throws UnknownIdException;
	
	public void markQuestionAsAnswered(int questionID) throws UnknownIdException;
	
	public void voteQuestion(int questionID, boolean vote) throws UnknownIdException;
	
	public Question addQuestion(int lectureID, String question) throws UnknownIdException;
	
	public void addAnswer(int questionID, String answer) throws UnknownIdException, IllegalAnswerException;
	
}
